package Domain.Game;

import Domain.Board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTest {

    @Test
    void testMoveSerialization() {
        Position start = new Position(4, 1);
        Position end = new Position(4, 3);
        Move move = new Move(start, end, 1);

        String serialized = move.toString();
        Move deserialized = Move.fromString(serialized);

        assertEquals(move, deserialized, "Deserialisierter Zug muss dem Original entsprechen.");
    }
}
