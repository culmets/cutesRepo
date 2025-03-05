package Domain.Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GameStateBuilderTest {

    @Test
    void testGameStateBuilderCreatesCorrectGameState() {
        GameState state = new GameState.Builder()
                .activePlayer("white")
                .boardState("dummyBoardState")
                .moveHistory("dummyMoveHistory")
                .activePlayerName("Alice")
                .otherPlayerName("Bob")
                .build();

        assertEquals("white", state.activePlayer());
        assertEquals("dummyBoardState", state.boardState());
        assertEquals("dummyMoveHistory", state.moveHistory());
        assertEquals("Alice", state.activePlayerName());
        assertEquals("Bob", state.otherPlayerName());
    }

    @Test
    void testGameStateSerializationIntegrity() {
        GameState original = new GameState.Builder()
                .activePlayer("black")
                .boardState("boardContent")
                .moveHistory("moves")
                .activePlayerName("Charlie")
                .otherPlayerName("Dana")
                .build();

        String serialized = original.toString();
        GameState deserialized = GameState.fromString(serialized);

        assertEquals(original, deserialized, "Deserialisierter GameState sollte dem Original entsprechen.");
    }
}
