package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {

    private Board board;
    private Knight whiteKnight;
    private Knight blackKnight;

    @BeforeEach
    void setUp() {
        board = new Board(true);
        whiteKnight = new Knight("white", new Position(4, 4));
        blackKnight = new Knight("black", new Position(2, 2));
        AbstractChessPiece whiteKing = new King("white", new Position(0, 4));
        AbstractChessPiece blackKing = new King("black", new Position(7, 4));
        board.placePiece(whiteKnight, new Position(4, 4));
        board.placePiece(blackKnight, new Position(2, 2));
        board.placePiece(whiteKing, new Position(0, 4));
        board.placePiece(blackKing, new Position(7, 4));
    }

    @Test
    void testValidKnightMoves() {
        List<Position> validMoves = whiteKnight.getValidMoves(board);

        assertTrue(validMoves.contains(new Position(6, 5)), "Springer sollte zwei Felder vorwärts und eins nach rechts ziehen können.");
        assertTrue(validMoves.contains(new Position(6, 3)), "Springer sollte zwei Felder vorwärts und eins nach links ziehen können.");
        assertTrue(validMoves.contains(new Position(2, 5)), "Springer sollte zwei Felder rückwärts und eins nach rechts ziehen können.");
        assertTrue(validMoves.contains(new Position(2, 3)), "Springer sollte zwei Felder rückwärts und eins nach links ziehen können.");
        assertTrue(validMoves.contains(new Position(5, 6)), "Springer sollte ein Feld vorwärts und zwei nach rechts ziehen können.");
        assertTrue(validMoves.contains(new Position(5, 2)), "Springer sollte ein Feld vorwärts und zwei nach links ziehen können.");
        assertTrue(validMoves.contains(new Position(3, 6)), "Springer sollte ein Feld rückwärts und zwei nach rechts ziehen können.");
        assertTrue(validMoves.contains(new Position(3, 2)), "Springer sollte ein Feld rückwärts und zwei nach links ziehen können.");
    }

    @Test
    void testCaptureEnemyPiece() {
        board.placePiece(new Pawn("black", new Position(6, 5)), new Position(6, 5));
        List<Position> validMoves = whiteKnight.getValidMoves(board);

        assertTrue(validMoves.contains(new Position(6, 5)), "Springer sollte die gegnerische Figur auf G6 schlagen können.");
    }

    @Test
    void testInvalidMoves() {
        List<Position> validMoves = whiteKnight.getValidMoves(board);

        assertFalse(validMoves.contains(new Position(5, 5)), "Springer sollte nicht diagonal ziehen können.");
        assertFalse(validMoves.contains(new Position(4, 6)), "Springer sollte nicht horizontal ziehen können.");
        assertFalse(validMoves.contains(new Position(6, 4)), "Springer sollte nicht vertikal ziehen können.");
    }

    @Test
    void testKingSafety() {
        board.placePiece(new Knight("black", new Position(2, 5)), new Position(2, 5));
        List<Position> validMoves = whiteKnight.getValidMoves(board);

        assertFalse(validMoves.contains(new Position(6, 5)), "Springer sollte keinen Zug machen, der den König im Schach lässt.");
        assertTrue(validMoves.contains(new Position(2, 5)), "Springer sollte den gegnerischen Springer schlagen können.");
    }
    @Test
    void testKingSafetyNoMovesAllowed() {
        board.placePiece(new Rook("black", new Position(0, 5)), new Position(0, 5));
        List<Position> validMoves = whiteKnight.getValidMoves(board);

        assertTrue(validMoves.isEmpty(), "Springer sollte keinen Zug machen, der den König im Schach lässt.");
    }

    @Test
    void testIgnorePieces() {
        board.placePiece(new Pawn("white", new Position(5, 4)), new Position(5, 4));
        board.placePiece(new Pawn("white", new Position(3, 4)), new Position(3, 4));

        List<Position> validMoves = whiteKnight.getValidMoves(board);

        assertTrue(validMoves.contains(new Position(6, 5)), "Springer sollte sich bewegen, auch wenn Figuren im Weg sind.");
        assertTrue(validMoves.contains(new Position(3, 6)), "Springer sollte sich bewegen, auch wenn Figuren im Weg sind.");
    }
}
