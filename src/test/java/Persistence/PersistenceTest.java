package Persistence;

import Domain.Game.Move;
import Domain.Board.Position;
import Domain.Game.MoveHistory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersistenceTest {

    @Test
    void testMoveCreation() {
        Move move = new Move(new Position(1, 4), new Position(3, 4), 1);
        assertEquals(new Position(1, 4), move.from());
        assertEquals(new Position(3, 4), move.to());
        assertEquals(1, move.moveNumber());
    }

    @Test
    void testMoveHistoryAddAndRetrieve() {
        MoveHistory history = new MoveHistory();
        Move move1 = new Move(new Position(1, 4), new Position(3, 4), 1);
        Move move2 = new Move(new Position(6, 4), new Position(4, 4), 2);

        history.addMove(move1);
        history.addMove(move2);

        List<Move> moves = history.getDemMoves();
        assertEquals(2, moves.size());
        assertEquals(move1, moves.get(0));
        assertEquals(move2, moves.get(1));
    }
}
