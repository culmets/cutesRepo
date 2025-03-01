package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {

    private Board board;
    private Bishop whiteBishop;
    private Bishop blackBishop;

    @BeforeEach
    void setUp() {
        board = new Board(true);
        whiteBishop = new Bishop("white", new Position(3, 3));
        blackBishop = new Bishop("black", new Position(6, 6));
        AbstractChessPiece whiteKing = new King("white", new Position(0, 4));
        AbstractChessPiece blackKing = new King("black", new Position(7, 4));
        board.placePiece(whiteBishop, new Position(3, 3));
        board.placePiece(blackBishop, new Position(6, 6));
        board.placePiece(whiteKing, new Position(0, 4));
        board.placePiece(blackKing, new Position(7, 4));
        board.printBoard();
    }

    @Test
    void testFreeMovement() {
        List<Position> validMoves = whiteBishop.getValidMoves(board);
        assertTrue(validMoves.contains(new Position(0, 0)), "Läufer sollte sich diagonal links oben bewegen können.");
        assertTrue(validMoves.contains(new Position(5, 5)), "Läufer sollte sich diagonal rechts unten bewegen können.");
        assertTrue(validMoves.contains(new Position(5, 1)), "Läufer sollte sich diagonal links unten bewegen können.");
        assertTrue(validMoves.contains(new Position(1, 5)), "Läufer sollte sich diagonal rechts oben bewegen können.");
    }

    @Test
    void testBlockedByOwnPiece() {
        board.placePiece(new Pawn("white", new Position(5, 5)), new Position(5, 5));
        List<Position> validMoves = whiteBishop.getValidMoves(board);
        assertFalse(validMoves.contains(new Position(6, 6)), "Läufer sollte nicht durch eigene Figuren ziehen können.");
        assertFalse(validMoves.contains(new Position(5, 5)), "Läufer sollte nicht auf ein Feld mit einer eigenen Figur ziehen können.");
    }

    @Test
    void testCaptureEnemyPiece() {
        board.placePiece(new Pawn("black", new Position(5, 5)), new Position(5, 5));
        List<Position> validMoves = whiteBishop.getValidMoves(board);
        assertTrue(validMoves.contains(new Position(5, 5)), "Läufer sollte eine gegnerische Figur diagonal schlagen können.");
    }

    @Test
    void testInvalidVerticalHorizontalMove() {
        List<Position> validMoves = whiteBishop.getValidMoves(board);
        assertFalse(validMoves.contains(new Position(3, 5)), "Läufer sollte nicht horizontal ziehen können.");
        assertFalse(validMoves.contains(new Position(1, 3)), "Läufer sollte nicht vertikal ziehen können.");
    }

    @Test
    void testKingSafety() {
        board.placePiece(new Rook("black", new Position(0, 0)), new Position(0, 0));
        List<Position> validMoves = whiteBishop.getValidMoves(board);
        assertFalse(validMoves.contains(new Position(1, 2)), "Läufer sollte keinen Zug machen, der den König im Schach lässt.");
        assertTrue(validMoves.contains(new Position(0, 0)), "Läufer sollte den Turm schlagen können, der den König bedroht.");
    }

    @Test
    void testKingSafetyNoMovesAllowed() {
        board.placePiece(new Rook("black", new Position(0, 5)), new Position(0, 5));
        List<Position> validMoves = whiteBishop.getValidMoves(board);
        assertTrue(validMoves.isEmpty(), "Läufer sollte sich nicht bewegen können wenn der den König bedroht.");
    }
}
