package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KingTest {

    private Board board;
    private King whiteKing;
    private King blackKing;

    @BeforeEach
    void setUp() {
        board = new Board(true);
        whiteKing = new King("white", new Position(4, 4));
        blackKing = new King("black", new Position(7, 4));
        board.placePiece(whiteKing, new Position(4, 4));
        board.placePiece(blackKing, new Position(7, 4));
    }

    @Test
    void testValidMoves() {
        List<Position> validMoves = whiteKing.getValidMoves(board);

        assertTrue(validMoves.contains(new Position(3, 4)), "König sollte sich ein Feld nach oben bewegen können.");
        assertTrue(validMoves.contains(new Position(5, 4)), "König sollte sich ein Feld nach unten bewegen können.");
        assertTrue(validMoves.contains(new Position(4, 3)), "König sollte sich ein Feld nach links bewegen können.");
        assertTrue(validMoves.contains(new Position(4, 5)), "König sollte sich ein Feld nach rechts bewegen können.");
        assertTrue(validMoves.contains(new Position(3, 3)), "König sollte sich diagonal links oben bewegen können.");
        assertTrue(validMoves.contains(new Position(3, 5)), "König sollte sich diagonal rechts oben bewegen können.");
        assertTrue(validMoves.contains(new Position(5, 3)), "König sollte sich diagonal links unten bewegen können.");
        assertTrue(validMoves.contains(new Position(5, 5)), "König sollte sich diagonal rechts unten bewegen können.");
    }

    @Test
    void testMoveIntoThreatenedField() {
        board.placePiece(new Rook("black", new Position(4, 6)), new Position(4, 6));
        List<Position> validMoves = whiteKing.getValidMoves(board);

        assertFalse(validMoves.contains(new Position(4, 5)), "König sollte sich nicht auf ein bedrohtes Feld bewegen können.");
    }

    @Test
    void testNoSpecialMoves() {
        List<Position> validMoves = whiteKing.getValidMoves(board);

        assertFalse(validMoves.contains(new Position(6, 4)), "König sollte keine zwei Felder ziehen können.");
        assertFalse(validMoves.contains(new Position(4, 6)), "König sollte keine zwei Felder horizontal ziehen können.");
    }

    @Test
    void testMoveOutOfCheck() {
        board.placePiece(new Rook("black", new Position(4, 5)), new Position(4, 5));

        List<Position> validMoves = whiteKing.getValidMoves(board);
        assertTrue(validMoves.contains(new Position(5, 4)), "König sollte sich aus dem Schach nach unten bewegen können.");
        assertTrue(validMoves.contains(new Position(3, 4)), "König sollte sich aus dem Schach nach oben bewegen können.");
    }

    @Test
    void testCantMoveIntoThreatenedFieldWhenAlreadyInCheck() {
        board.placePiece(new Rook("black", new Position(4, 6)), new Position(4, 6));

        List<Position> validMoves = whiteKing.getValidMoves(board);
        assertFalse(validMoves.contains(new Position(4, 5)), "König sollte sich nicht in ein bedrohtes Feld bewegen können.");
        assertFalse(validMoves.contains(new Position(4, 3)), "König sollte sich nicht in ein bedrohtes Feld bewegen können.");
    }




    @Test
    void testCantMoveItselfIntoCheck() {
        board.placePiece(new Rook("black", new Position(3, 5)), new Position(3, 5));
        List<Position> validMoves = whiteKing.getValidMoves(board);

        assertFalse(validMoves.contains(new Position(3,4)), "König sollte keine Züge machen können, die ihn in Schach bringen.");
    }

    @Test
    void testCaptureThreat() {
        board.placePiece(new Rook("black", new Position(3, 3)), new Position(3, 3));
        List<Position> validMoves = whiteKing.getValidMoves(board);

        assertTrue(validMoves.contains(new Position(3, 3)), "König sollte eine gegnerische Figur schlagen können, die ihn bedroht.");
    }

    @Test
    void testNoMovesWhileSurrounded() {
        board.placePiece(new Pawn("white", new Position(3, 3)), new Position(3, 3));
        board.placePiece(new Pawn("white", new Position(3, 4)), new Position(3, 4));
        board.placePiece(new Pawn("white", new Position(3, 5)), new Position(3, 5));
        board.placePiece(new Pawn("white", new Position(4, 3)), new Position(4, 3));
        board.placePiece(new Pawn("white", new Position(4, 5)), new Position(4, 5));
        board.placePiece(new Pawn("white", new Position(5, 3)), new Position(5, 3));
        board.placePiece(new Pawn("white", new Position(5, 4)), new Position(5, 4));
        board.placePiece(new Pawn("white", new Position(5, 5)), new Position(5, 5));

        List<Position> validMoves = whiteKing.getValidMoves(board);

        assertTrue(validMoves.isEmpty(), "König sollte keine Züge haben, wenn er umzingelt ist.");
    }
}
