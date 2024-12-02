package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {

    private Board board;
    private Queen whiteQueen;
    private Queen blackQueen;

    @BeforeEach
    void setUp() {
        board = new Board(true);
        whiteQueen = new Queen("white", new Position(3, 3)); // D4
        blackQueen = new Queen("black", new Position(6, 6)); // G7
        AbstractChessPiece whiteKing = new King("white", new Position(0, 4));
        AbstractChessPiece blackKing = new King("black", new Position(7, 4));
        board.placePiece(whiteQueen, new Position(3, 3));
        board.placePiece(blackQueen, new Position(6, 6));
        board.placePiece(whiteKing, new Position(0, 4));
        board.placePiece(blackKing, new Position(7, 4));
    }

    @Test
    void testFreeMovement() {
        List<Position> validMoves = whiteQueen.getValidMoves(board);
        assertTrue(validMoves.contains(new Position(3, 7)), "Dame sollte sich nach rechts bewegen können.");
        assertTrue(validMoves.contains(new Position(3, 0)), "Dame sollte sich nach links bewegen können.");
        assertTrue(validMoves.contains(new Position(0, 3)), "Dame sollte sich nach unten bewegen können.");
        assertTrue(validMoves.contains(new Position(7, 3)), "Dame sollte sich nach unten bewegen können.");
        assertTrue(validMoves.contains(new Position(0, 0)), "Dame sollte sich diagonal (links unten) bewegen können.");
        assertTrue(validMoves.contains(new Position(6, 0)), "Dame sollte sich diagonal (links oben) bewegen können.");
        assertTrue(validMoves.contains(new Position(6, 6)), "Dame sollte sich diagonal (rechts oben) bewegen können.");
        assertTrue(validMoves.contains(new Position(0, 6)), "Dame sollte sich diagonal (rechts unten) bewegen können.");
    }

    @Test
    void testBlockedByOwnPiece() {
        board.placePiece(new Pawn("white", new Position(3, 5)), new Position(3, 5));
        board.placePiece(new Pawn("white", new Position(5, 5)), new Position(5, 5));

        List<Position> validMoves = whiteQueen.getValidMoves(board);
        assertFalse(validMoves.contains(new Position(3, 6)), "Dame sollte nicht durch eigene Figuren ziehen können.");
        assertFalse(validMoves.contains(new Position(6, 6)), "Dame sollte nicht diagonal durch eigene Figuren ziehen können.");
    }

    @Test
    void testCaptureEnemyPiece() {
        board.placePiece(new Pawn("black", new Position(3, 5)), new Position(3, 5));
        board.placePiece(new Pawn("black", new Position(5, 5)), new Position(5, 5));

        List<Position> validMoves = whiteQueen.getValidMoves(board);
        assertTrue(validMoves.contains(new Position(3, 5)), "Dame sollte eine gegnerische Figur horizontal schlagen können.");
        assertTrue(validMoves.contains(new Position(5, 5)), "Dame sollte eine gegnerische Figur diagonal schlagen können.");
    }

    @Test
    void testInvalidKnightMove() {
        List<Position> validMoves = whiteQueen.getValidMoves(board);
        assertFalse(validMoves.contains(new Position(5, 4)), "Dame sollte nicht wie ein Springer ziehen können.");
    }

    @Test
    void testKingSafety() {
        board.placePiece(new Rook("black", new Position(0, 3)), new Position(0, 3));
        List<Position> validMoves = whiteQueen.getValidMoves(board);
        assertFalse(validMoves.contains(new Position(3, 2)), "Dame sollte keinen Zug machen, der den König im Schach lässt.");
        assertTrue(validMoves.contains(new Position(0, 3)), "Dame sollte Figur die den König bedroht schlagen können");
    }
    @Test
    void testKingSafetyNoMovesAllowes() {
        board.placePiece(new Rook("black", new Position(0, 5)), new Position(0, 5));
        List<Position> validMoves = whiteQueen.getValidMoves(board);
        assertTrue(validMoves.isEmpty(), "Dame sollte keinen Zug machen, der den König im Schach lässt.");
    }
}
