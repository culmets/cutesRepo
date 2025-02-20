package Persistence;

import Domain.Game.Move;
import Domain.Game.GameRecord;
import Domain.Game.MoveHistory;
import Domain.Game.MoveType;
import Domain.Board.Position;
import Domain.Persistence.GameRecordRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileGameRecordRepoTest {

    private static final String TEST_FOLDER = "test_game_records";

    @BeforeAll
    static void cleanUpBeforeTests() throws IOException {
        Path folder = Paths.get(TEST_FOLDER);
        if (Files.exists(folder)) {
            Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

@AfterEach
void cleanUp() throws IOException {
    Path folder = Paths.get(TEST_FOLDER);
    if (Files.exists(folder)) {
        Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}

    @Test
    void testSaveAndRetrieveGameRecord() {
        FileGameRecordRepo repository = new FileGameRecordRepo(TEST_FOLDER);

        MoveHistory history = new MoveHistory();
        Move move = new Move(new Position(1, 4), new Position(3, 4), 1, MoveType.NORMAL);
        history.addMove(move);
        GameRecord record = new GameRecord("Alice", "Bob", "Alice", history.getDemMoves(), LocalDateTime.of(2023, 3, 1, 12, 0));

        repository.saveGameRecord(record);

        List<GameRecord> records = repository.getGameRecordsForPlayer("Alice");
        assertFalse(records.isEmpty(), "Es sollte mind einen GameRecord geben.");
        GameRecord retrieved = records.getFirst();
        assertEquals("Alice", retrieved.getWhitePlayer(), "White player sollte Alice sein.");
        assertEquals("Bob", retrieved.getBlackPlayer(), "Black player sollte Bob sein.");
        assertEquals("Alice", retrieved.getWinner(), "Winner sollte Alice sein.");
        assertEquals(1, retrieved.getMoveHistory().size(), "MoveHistory sollte 1 Move enthalten.");
    }

    @Test
    void testMultipleGameRecords() {
        GameRecordRepo repository = new FileGameRecordRepo(TEST_FOLDER);

        MoveHistory history1 = new MoveHistory();
        history1.addMove(new Move(new Position(1, 4), new Position(3, 4), 1, MoveType.NORMAL));
        LocalDateTime date1 = LocalDateTime.of(2023, 3, 1, 12, 0);
        GameRecord record1 = new GameRecord("Alice", "Bob", "Alice", history1.getDemMoves(), date1);
        repository.saveGameRecord(record1);

        MoveHistory history2 = new MoveHistory();
        history2.addMove(new Move(new Position(6, 4), new Position(4, 4), 1, MoveType.NORMAL));
        LocalDateTime date2 = LocalDateTime.of(2023, 3, 2, 14, 0);
        GameRecord record2 = new GameRecord("Alice", "Charlie", "Alice", history2.getDemMoves(), date2);
        repository.saveGameRecord(record2);

        List<GameRecord> recordsForAlice = repository.getGameRecordsForPlayer("Alice");
        assertEquals(2, recordsForAlice.size(), "Alice sollte 2 Records haben.");
    }

    @Test
    void testEmptyRepository() {
        GameRecordRepo repository = new FileGameRecordRepo(TEST_FOLDER);
        List<GameRecord> records = repository.getGameRecordsForPlayer("null");
        assertTrue(records.isEmpty(), "Für einen nicht existierenden Spieler sollten keine Records zurückgegeben werden.");
    }

}
