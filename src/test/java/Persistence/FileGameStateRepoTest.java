package Persistence;

import Domain.Game.GameState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileGameStateRepoTest {
    private static final String TEST_FOLDER = "test_game_states";
    private FileGameStateRepo repository;

    @BeforeEach
    void setUp() throws IOException {
        Path folderPath = Paths.get(TEST_FOLDER);
        if (Files.exists(folderPath)) {
            deleteDirectoryRecursively(folderPath);
        }
        Files.createDirectories(folderPath);
        repository = new FileGameStateRepo(TEST_FOLDER);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path folderPath = Paths.get(TEST_FOLDER);
        if (Files.exists(folderPath)) {
            deleteDirectoryRecursively(folderPath);
        }
    }

    private void deleteDirectoryRecursively(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .forEach(p -> {
                    try {
                        Files.delete(p);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void testSaveAndLoadGameState() {
        GameState state = new GameState("white", "dummyBoard", "dummyHistory", LocalDateTime.of(2023, 3, 1, 12, 0));
        String fileName = repository.saveGameState(state);
        Optional<GameState> loaded = repository.loadGameState(fileName);
        assertTrue(loaded.isPresent(), "Es sollte ein GameState geladen werden.");
        GameState loadedState = loaded.get();
        assertEquals(state.activePlayer(), loadedState.activePlayer(), "Der aktive Spieler sollte übereinstimmen.");
        assertEquals(state.boardState(), loadedState.boardState(), "Der Board-Status sollte übereinstimmen.");
        assertEquals(state.moveHistory(), loadedState.moveHistory(), "Die MoveHistory sollte übereinstimmen.");
    }

    @Test
    void testLoadNoGameState() {
        Optional<GameState> loaded = repository.loadGameState("doesnt exist");
        assertTrue(loaded.isEmpty(), "Es sollte kein GameState geladen werden, wenn der Ordner leer ist.");
    }
}
