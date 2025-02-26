package Persistence;

import Application.LeaderboardEntry;
import Domain.Persistence.LeaderboardRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class FileLeaderboardRepo implements LeaderboardRepository {

    private final Path filePath;

    public FileLeaderboardRepo(String baseFolder) {
        this.filePath = Paths.get(baseFolder, "leaderboard.txt");
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            Path parent = filePath.getParent();
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Konnte den Leaderboard-Dateipfad nicht erstellen: " + filePath, e);
        }
    }

    @Override
    public void updateWin(String playerName) {
        Map<String, Integer> data = loadLeaderboard();
        data.put(playerName, data.getOrDefault(playerName, 0) + 1);
        saveLeaderboard(data);
    }

    @Override
    public List<LeaderboardEntry> getLeaderboard() {
        Map<String, Integer> data = loadLeaderboard();
        return data.entrySet().stream()
                .map(e -> new LeaderboardEntry(e.getKey(), e.getValue()))
                .sorted(Comparator.comparingInt(LeaderboardEntry::winCount).reversed())
                .collect(Collectors.toList());
    }

    private void saveLeaderboard(Map<String, Integer> data) {
        List<String> lines = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            lines.add(entry.getKey() + ";" + entry.getValue());
        }
        try {
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Konnte das Leaderboard nicht speichern: " + filePath, e);
        }
    }


    private Map<String, Integer> loadLeaderboard() {
        Map<String, Integer> data = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(this.filePath);
            for (String line : lines) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    int count = Integer.parseInt(parts[1].trim());
                    data.put(name, count);
                }
            }
        } catch (IOException e) {
            // leere Map zurückgeben
        }
        return data;
    }

    public void printLeaderboard() {
        List<LeaderboardEntry> entries = getLeaderboard();
        System.out.printf("%-20s | %s%n", "Spieler", "Siege");
        System.out.println("----------------------------------");
        for (LeaderboardEntry entry : entries) {
            System.out.printf("%-20s | %d%n", entry.playerName(), entry.winCount());
        }
    }
}

