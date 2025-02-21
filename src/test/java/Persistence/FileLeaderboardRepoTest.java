package Persistence;
//import Persistence.LeaderboardRepo;
import Application.LeaderboardEntry;
import Domain.Persistence.LeaderboardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileLeaderboardRepoTest {

        private static final String TEST_FOLDER = "test_leaderboard";

        @BeforeAll
        static void cleanUpBeforeTests() throws IOException {
            Path folder = Paths.get(TEST_FOLDER);
            if (Files.exists(folder)) {
                Files.walkFileTree(folder, new SimpleFileVisitor<>() {
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
        void cleanUpAfterTest() throws IOException {
            Path folder = Paths.get(TEST_FOLDER);
            if (Files.exists(folder)) {
                Files.walkFileTree(folder, new SimpleFileVisitor<>() {
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
        void testUpdateNewPlayer() {
            LeaderboardRepository repository = new FileLeaderboardRepo(TEST_FOLDER);
            repository.updateWin("Alice");

            List<LeaderboardEntry> leaderboard = repository.getLeaderboard();
            assertEquals(1, leaderboard.size(), "Es sollte genau ein Eintrag vorhanden sein.");
            LeaderboardEntry entry = leaderboard.getFirst();
            assertEquals("Alice", entry.playerName(), "Der Eintrag sollte 'Alice' beinhalten.");
            assertEquals(1, entry.winCount(), "Alice sollte 1 Sieg haben.");
        }

        @Test
        void testUpdateExistingPlayer() {
            LeaderboardRepository repository = new FileLeaderboardRepo(TEST_FOLDER);
            repository.updateWin("Alice");
            repository.updateWin("Alice");

            List<LeaderboardEntry> leaderboard = repository.getLeaderboard();
            assertEquals(1, leaderboard.size(), "Es sollte nur ein Eintrag für Alice geben.");
            LeaderboardEntry entry = leaderboard.getFirst();
            assertEquals("Alice", entry.playerName(), "Der Eintrag sollte 'Alice' sein.");
            assertEquals(2, entry.winCount(), "Alice sollte 2 Siege haben.");
        }

        @Test
        void testMultiplePlayersAndSorting() {
            LeaderboardRepository repository = new FileLeaderboardRepo(TEST_FOLDER);
            repository.updateWin("Alice");
            repository.updateWin("Bob");
            repository.updateWin("Alice");
            repository.updateWin("Charlie");
            repository.updateWin("Bob");
            repository.updateWin("Alice");

            List<LeaderboardEntry> leaderboard = repository.getLeaderboard();
            assertEquals(3, leaderboard.size(), "Es sollten 3 Einträge vorhanden sein.");

            assertEquals("Alice", leaderboard.get(0).playerName());
            assertEquals(3, leaderboard.get(0).winCount());

            assertEquals("Bob", leaderboard.get(1).playerName());
            assertEquals(2, leaderboard.get(1).winCount());

            assertEquals("Charlie", leaderboard.get(2).playerName());
            assertEquals(1, leaderboard.get(2).winCount());
        }
}

