package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RookTest {

    private Board board;
    private Rook whiteRook;
    private Rook blackRook;

    @BeforeEach
    void setUp() {
        board = new Board(true);
        whiteRook = new Rook("white", new Position(3, 3)); // D4
        blackRook = new Rook("black", new Position(5, 3)); // D6
        AbstractChessPiece whiteKing = new King("white", new Position(0, 4));
        AbstractChessPiece blackKing = new King("black", new Position(7, 4));
        board.placePiece(whiteRook, new Position(3, 3));
        board.placePiece(blackRook, new Position(5, 3));
        board.placePiece(whiteKing, new Position(0, 4));
        board.placePiece(blackKing, new Position(7, 4));
    }

    @Test
    void testFreeMovement() {
        List<Position> validMoves = whiteRook.getValidMoves(board);
        assertTrue(validMoves.contains(new Position(3, 0)), "Turm sollte sich nach links bewegen können.");
        assertTrue(validMoves.contains(new Position(3, 7)), "Turm sollte sich nach rechts bewegen können.");
        assertTrue(validMoves.contains(new Position(0, 3)), "Turm sollte sich nach unten bewegen können.");
        assertTrue(validMoves.contains(new Position(4, 3)), "Turm sollte sich nach oben bewegen können.");
    }

    @Test
    void testBlockedByOwnPiece() {
        board.placePiece(new Pawn("white", new Position(3, 5)), new Position(3, 5));
        List<Position> validMoves = whiteRook.getValidMoves(board);
        assertFalse(validMoves.contains(new Position(3, 6)), "Turm sollte nicht durch eigene Figuren ziehen können.");
        assertFalse(validMoves.contains(new Position(3, 5)), "Turm sollte nicht auf ein Feld mit einer eigenen Figur ziehen können.");
    }

    @Test
    void testCaptureEnemyPiece() {
        board.placePiece(new Pawn("black", new Position(3, 5)), new Position(3, 5));
        List<Position> validMoves = whiteRook.getValidMoves(board);
        assertTrue(validMoves.contains(new Position(3, 5)), "Turm sollte eine gegnerische Figur schlagen können.");
    }

    @Test
    void testNoDiagonalMovement() {
        List<Position> validMoves = whiteRook.getValidMoves(board);
        assertFalse(validMoves.contains(new Position(4, 4)), "Turm sollte nicht diagonal ziehen können.");
        assertFalse(validMoves.contains(new Position(2, 2)), "Turm sollte nicht diagonal ziehen können.");
    }

    @Test
    void testKingSafety() {
        board.placePiece(new Rook("black", new Position(0, 3)), new Position(0, 3));
        List<Position> validMoves = whiteRook.getValidMoves(board);
        assertFalse(validMoves.contains(new Position(3, 2)), "Weißer Turm sollte keinen Zug machen, der den König im Schach lässt.");
        assertTrue(validMoves.contains(new Position(0, 3))); // Kann den schwarzen Rook schlagen
    }
    @Test
    void testKingSafetyNoMovesAllowed() {
        board.placePiece(new Rook("black", new Position(0, 5)), new Position(0, 5));
        List<Position> validMoves = whiteRook.getValidMoves(board);
        assertTrue(validMoves.isEmpty(), "Turm sollte keinen Zug machen, der den König im Schach lässt.");
    }
}
