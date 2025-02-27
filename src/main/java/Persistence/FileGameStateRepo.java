package Persistence;

import Domain.Game.GameState;
import Domain.Persistence.GameStateRepo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class FileGameStateRepo implements GameStateRepo {

    private final Path baseFolder;

    public FileGameStateRepo(String baseFolder) {
        this.baseFolder = Paths.get(baseFolder);
        ensureDirectoryExists();
    }

    private void ensureDirectoryExists() {
        try {
            if (!Files.exists(baseFolder)) {
                Files.createDirectories(baseFolder);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create base folder: " + baseFolder, e);
        }
    }

    public static String generateUniqueFileName(String activePlayerName, String otherPlayerName) {
        String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return "game_record_" + timestamp + "_" + activePlayerName + "Und" + otherPlayerName + ".txt";
    }

    @Override
    public String saveGameState(GameState state) {
        String fileName = generateUniqueFileName(state.activePlayerName(), state.otherPlayerName());
        Path filePath = baseFolder.resolve(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE_NEW)) {
            writer.write(state.toString());
            writer.newLine();
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error saving game state to " + filePath, e);
        }
    }


    private Optional<GameState> readGameStateFromFile(Path filePath) {
        try {
            String content = Files.readString(filePath);
            if (content != null && !content.isBlank()) {
                return Optional.of(GameState.fromString(content));
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public List<String> listGameStateFileNames() {
        try {
            return Files.list(baseFolder)
                    .filter(p -> p.getFileName().toString().startsWith("game_state_"))
                    .map(p -> p.getFileName().toString())
                    .sorted()  // Sortierung kann angepasst werden, z.B. chronologisch
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<GameState> loadGameState(String fileName) {
        Path filePath = baseFolder.resolve(fileName);
        if (!Files.exists(filePath)) {
            return Optional.empty();
        }
        return readGameStateFromFile(filePath);
    }

    @Override
    public void deleteGameState(String fileName) {
        Path filePath = baseFolder.resolve(fileName);
        try {
            Files.deleteIfExists(filePath);
            System.out.println("GameState-Datei " + fileName + " wurde gelöscht.");
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Löschen des Spielstands: " + fileName, e);
        }

    }
}


