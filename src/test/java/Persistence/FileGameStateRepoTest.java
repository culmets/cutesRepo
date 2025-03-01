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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        GameState state = new GameState("white", "dummyBoard", "dummyHistory", "active","other");
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

    @Test
    void testLoadGameStateByFileName() {
        GameState state = new GameState("white", "dummyBoard", "dummyHistory", "first", "second");
        repository.saveGameState(state);
        List<String> names = repository.listGameStateFileNames();
        assertFalse(names.isEmpty(), "Es sollte mindestens ein gespeicherter Spielstand vorhanden sein.");
        String fileName = names.getFirst();
        Optional<GameState> loaded = repository.loadGameState(fileName);
        assertTrue(loaded.isPresent(), "Der Spielstand sollte anhand des Dateinamens geladen werden können.");
        GameState loadedState = loaded.get();
        assertEquals(state.activePlayer(), loadedState.activePlayer(), "Der aktive Spieler muss übereinstimmen.");
        assertEquals(state.boardState(), loadedState.boardState(), "Der Board-Status muss übereinstimmen.");
        assertEquals(state.moveHistory(), loadedState.moveHistory(), "Die MoveHistory muss übereinstimmen.");
    }

    @Test
    void testListGameStateFileNamesAfterSavingMultipleStates() {
        GameState state1 = new GameState("white", "dummyBoard1", "dummyHistory1", "either","other");
        repository.saveGameState(state1);
        GameState state2 = new GameState("black", "dummyBoard2", "dummyHistory2", "either2","other2");
        repository.saveGameState(state2);
        List<String> names = repository.listGameStateFileNames();
        assertEquals(2, names.size(), "Es sollten zwei gespeicherte Spielstände vorhanden sein.");
    }

    @Test
    void testPlayerNamesAfterSerialization() {
        String whitePlayer = "Alice";
        String blackPlayer = "Bob";
        String boardState = "dummyBoardState";
        String moveHistory = "dummyMoveHistory";
        GameState state = new GameState("white", boardState, moveHistory, whitePlayer, blackPlayer);

        String serialized = state.toString();

        GameState loadedState = GameState.fromString(serialized);

        assertEquals(whitePlayer, loadedState.activePlayerName(), "Der activePlayer muss 'Alice' sein.");
        assertEquals(blackPlayer, loadedState.otherPlayerName(), "Der BlackPlayer muss 'Bob' sein.");
    }

    @Test
    void testDeleteGameState() {
        GameState state = new GameState("white", "dummyBoard", "dummyHistory", "either","other");
        repository.saveGameState(state);

        List<String> filesBefore = repository.listGameStateFileNames();
        assertFalse(filesBefore.isEmpty(), "Es sollte mindestens eine GameState-Datei vorhanden sein.");

        String fileNameToDelete = filesBefore.getFirst();
        repository.deleteGameState(fileNameToDelete);

        List<String> filesAfter = repository.listGameStateFileNames();
        assertFalse(filesAfter.contains(fileNameToDelete), "Die GameState-Datei sollte gelöscht worden sein.");
    }
}
