package Domain.Game;

import Domain.Board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTest {

    @Test
    void testMoveSerialization() {
        Position start = new Position(4, 1); // e2
        Position end = new Position(4, 3);   // e4
        Move move = new Move(start, end, 1);

        String serialized = move.toString(); // z. B. "e2,e4,1,normal"
        Move deserialized = Move.fromString(serialized);

        assertEquals(move, deserialized, "Deserialisierter Zug muss dem Original entsprechen.");
    }
}
