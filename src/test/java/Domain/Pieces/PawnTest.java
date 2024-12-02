package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {

    private Board board;
    private Pawn whitePawn;
    private Pawn blackPawn;

    @BeforeEach
    void setUp() {
        board = new Board(true); //leeres board erstellen
        whitePawn = new Pawn("white", new Position(1, 4)); // E2
        blackPawn = new Pawn("black", new Position(6, 4)); // E7
        AbstractChessPiece whiteKing = new King("white", new Position(0,4));
        AbstractChessPiece blackKing = new King("black", new Position(7,3));
        board.placePiece(whitePawn, new Position(1, 4));
        board.placePiece(blackPawn, new Position(6, 4));
        board.placePiece(whiteKing, new Position(0, 4));
        board.placePiece(blackKing, new Position(7, 3));
    }

    @Test
    void testNormalForwardMove() {
        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertTrue(whiteMoves.contains(new Position(2, 4)), "Weißer Bauer sollte ein Feld vorwärts ziehen können.");
        assertTrue(whiteMoves.contains(new Position(3, 4)), "Weißer Bauer sollte zwei Felder vorwärts ziehen können.");

        List<Position> blackMoves = blackPawn.getValidMoves(board);
        assertTrue(blackMoves.contains(new Position(5, 4)), "Schwarzer Bauer sollte ein Feld vorwärts ziehen können.");
        assertTrue(blackMoves.contains(new Position(4, 4)), "Schwarzer Bauer sollte zwei Felder vorwärts ziehen können.");
    }

    @Test
    void testDiagonalCapture() {
        board.placePiece(new Pawn("black", new Position(2, 3)), new Position(2, 3)); // D3
        board.placePiece(new Pawn("black", new Position(2, 5)), new Position(2, 5)); // F3

        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertTrue(whiteMoves.contains(new Position(2, 3)), "Weißer Bauer sollte die schwarze Figur auf D3 schlagen können.");
        assertTrue(whiteMoves.contains(new Position(2, 5)), "Weißer Bauer sollte die schwarze Figur auf F3 schlagen können.");
    }

    @Test
    void testDiagonalCaptureBlockedByOwnPiece() {
        board.placePiece(new Pawn("white", new Position(2, 3)), new Position(2, 3)); // D3
        board.placePiece(new Pawn("white", new Position(2, 5)), new Position(2, 5)); // F3

        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertFalse(whiteMoves.contains(new Position(2, 3)), "Weißer Bauer sollte keine eigene Figur auf D3 schlagen können.");
        assertFalse(whiteMoves.contains(new Position(2, 5)), "Weißer Bauer sollte keine eigene Figur auf F3 schlagen können.");
    }


    @Test
    void testInvalidBackwardMove() {
        assertFalse(whitePawn.getValidMoves(board).contains(new Position(0, 4)), "Bauer darf nicht rückwärts ziehen.");
    }

    @Test
    void testPathBlocked() {
        board.placePiece(new Pawn("white", new Position(2, 4)), new Position(2, 4));

        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertFalse(whiteMoves.contains(new Position(3, 4)), "Weißer Bauer sollte nicht zwei Felder ziehen können, wenn der Weg blockiert ist.");
        assertFalse(whiteMoves.contains(new Position(2, 4)), "Weißer Bauer sollte nicht auf ein besetztes Feld ziehen können.");
    }
    @Test
    public void testKingSafety() {
        board.placePiece(new Pawn("black", new Position(1, 3)), new Position(1, 3));
        board.printBoard();

        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertTrue(whiteMoves.isEmpty(), "Weißer Bauer sollte keinen Zug machen, der den König im Schach lässt.");
    }

}
