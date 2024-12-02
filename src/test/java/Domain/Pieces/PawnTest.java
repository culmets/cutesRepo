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
        whitePawn = new Pawn("white", new Position(1, 4)); // weißer Bauer auf E2
        blackPawn = new Pawn("black", new Position(6, 4)); // schwarzer Bauer auf E7
        AbstractChessPiece whiteKing = new King("white", new Position(0,5));
        AbstractChessPiece blackKing = new King("black", new Position(7,4));
        board.placePiece(whitePawn, new Position(1, 4));
        board.placePiece(blackPawn, new Position(6, 4));
        board.placePiece(whiteKing, new Position(0, 4));
        board.placePiece(blackKing, new Position(7, 3));
    }

    @Test
    void testNormalForwardMove() {
        // Test für weißen Bauern
        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertTrue(whiteMoves.contains(new Position(2, 4)), "Weißer Bauer sollte ein Feld vorwärts ziehen können.");
        assertTrue(whiteMoves.contains(new Position(3, 4)), "Weißer Bauer sollte zwei Felder vorwärts ziehen können.");

        // Test für schwarzen Bauern
        List<Position> blackMoves = blackPawn.getValidMoves(board);
        assertTrue(blackMoves.contains(new Position(5, 4)), "Schwarzer Bauer sollte ein Feld vorwärts ziehen können.");
        assertTrue(blackMoves.contains(new Position(4, 4)), "Schwarzer Bauer sollte zwei Felder vorwärts ziehen können.");
    }

    @Test
    void testDiagonalCapture() {
        // Setze eine gegnerische Figur diagonal
        board.placePiece(new Pawn("black", new Position(2, 3)), new Position(2, 3)); // Schwarz auf D3
        board.placePiece(new Pawn("black", new Position(2, 5)), new Position(2, 5)); // Schwarz auf F3

        // Prüfen, ob diagonale Schläge möglich sind
        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertTrue(whiteMoves.contains(new Position(2, 3)), "Weißer Bauer sollte die schwarze Figur auf D3 schlagen können.");
        assertTrue(whiteMoves.contains(new Position(2, 5)), "Weißer Bauer sollte die schwarze Figur auf F3 schlagen können.");
    }

    @Test
    void testInvalidBackwardMove() {
        // Weißer Bauer sollte nicht rückwärts ziehen können
        assertFalse(whitePawn.getValidMoves(board).contains(new Position(0, 4)), "Weißer Bauer darf nicht rückwärts ziehen.");
    }

    @Test
    void testPathBlocked() {
        // Blockiere den Weg eines weißen Bauern
        board.placePiece(new Pawn("white", new Position(2, 4)), new Position(2, 4)); // Weißer Bauer blockiert

        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertFalse(whiteMoves.contains(new Position(3, 4)), "Weißer Bauer sollte nicht zwei Felder ziehen können, wenn der Weg blockiert ist.");
        assertFalse(whiteMoves.contains(new Position(2, 4)), "Weißer Bauer sollte nicht auf ein besetztes Feld ziehen können.");
    }
    @Test
    public void testKingSafety() {
        // Setze eine Bedrohung für den König
        board.placePiece(new Pawn("black", new Position(6, 2)), new Position(6, 2)); // Schwarzer Bauer bedroht
        //  board.placePiece(new King("white", new Position(1, 2)), new Position(1, 2)); // Weißer König auf C2

        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertFalse(whiteMoves.contains(new Position(2, 4)), "Weißer Bauer sollte keinen Zug machen, der den König im Schach lässt.");
    }

}
