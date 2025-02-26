package Persistence;

import Domain.Board.Position;
import Domain.Game.GameRecord;
import Domain.Game.Move;
import Domain.Game.MoveHistory;
import Domain.Game.MoveType;
import Domain.Persistence.GameRecordRepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileGameRecordRepo implements GameRecordRepo {

    private final String baseFolder;

    public FileGameRecordRepo(String baseFolder) {
        this.baseFolder = baseFolder;
        ensureDirectoryExists();
    }

    private void ensureDirectoryExists() {
        Path path = Paths.get(baseFolder);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("Konnte den Basisordner nicht erstellen: " + baseFolder, e);
            }
        }
    }

    public static String generateUniqueFileName() {
        String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Random rand = new Random();
        return "game_record_" + timestamp + "_" + rand.nextInt(100000)+ ".txt";
    }

    @Override
    public void saveGameRecord(GameRecord record) {
        String fileName = generateUniqueFileName();
        Path filePath = Paths.get(baseFolder, fileName);
        System.out.println("Speichere GameRecord in Datei: " + filePath.toAbsolutePath());
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(serialize(record));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Speichern des GameRecords", e);
        }
    }

    @Override
    public List<GameRecord> getGameRecordsForPlayer(String playerName) {
        List<GameRecord> records = new ArrayList<>();
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(baseFolder), "*.txt");
            for (Path path : stream) {
                try (BufferedReader reader = Files.newBufferedReader(path)) {
                    String line = reader.readLine();
                    if (line != null) {
                        GameRecord record = deserialize(line);
                        if (record.whitePlayer().equalsIgnoreCase(playerName) ||
                                record.blackPlayer().equalsIgnoreCase(playerName)) {
                            records.add(record);
                        }
                    }
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return records;
    }

    // Format: whitePlayer;blackPlayer;winner;gameDate;move1|move2|...
    // Format Move: start,end,moveNumber,moveType
    private String serialize(GameRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append(record.whitePlayer()).append(";");
        sb.append(record.blackPlayer()).append(";");
        sb.append(record.winner()).append(";");
        sb.append(record.gameDate().toString()).append(";");
        List<Move> moves = record.moveHistory();
        for (int i = 0; i < moves.size(); i++) {
            sb.append(moves.get(i).toString());
            if (i < moves.size() - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

    private GameRecord deserialize(String line) {
        String[] parts = line.split(";", 5);
        if (parts.length < 5) {
            throw new RuntimeException("Invalid record format: " + line);
        }
        String whitePlayer = parts[0];
        String blackPlayer = parts[1];
        String winner = parts[2];
        LocalDateTime gameDate = LocalDateTime.parse(parts[3]);
        String movesStr = parts[4];

        MoveHistory history = new MoveHistory();
        if (!movesStr.isEmpty()) {
            String[] movesArr = movesStr.split("\\|");
            for (String moveStr : movesArr) {
                Move move = parseMove(moveStr);
                history.addMove(move);
            }
        }
        return new GameRecord(whitePlayer, blackPlayer, winner, history.getDemMoves(), gameDate);
    }

    private Move parseMove(String moveStr) {
        String[] parts = moveStr.split(",");
        if (parts.length != 4) {
            throw new RuntimeException("Invalid move format: " + moveStr);
        }
        Position start = Position.fromString(parts[0]);
        Position end = Position.fromString(parts[1]);
        int moveNumber = Integer.parseInt(parts[2]);
        MoveType moveType = MoveType.valueOf(parts[3].toUpperCase());
        return new Move(start, end, moveNumber, moveType);
    }
}
