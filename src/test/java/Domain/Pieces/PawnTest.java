package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;
import org.junit.jupiter.api.BeforeAll;
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
        board = new Board(true);
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
        assertTrue(whiteMoves.contains(new Position(2, 4)), "Bauer sollte ein Feld vorwärts ziehen können.");
        assertTrue(whiteMoves.contains(new Position(3, 4)), "Bauer sollte zwei Felder vorwärts ziehen können.");

        List<Position> blackMoves = blackPawn.getValidMoves(board);
        assertTrue(blackMoves.contains(new Position(5, 4)), "Bauer sollte ein Feld vorwärts ziehen können.");
        assertTrue(blackMoves.contains(new Position(4, 4)), "Bauer sollte zwei Felder vorwärts ziehen können.");
    }

    @Test
    void testDiagonalCapture() {
        board.placePiece(new Pawn("black", new Position(2, 3)), new Position(2, 3)); // D3
        board.placePiece(new Pawn("black", new Position(2, 5)), new Position(2, 5)); // F3

        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertTrue(whiteMoves.contains(new Position(2, 3)), "Bauer sollte die schwarze Figur auf D3 schlagen können.");
        assertTrue(whiteMoves.contains(new Position(2, 5)), "Bauer sollte die schwarze Figur auf F3 schlagen können.");
    }

    @Test
    void testDiagonalCaptureBlockedByOwnPiece() {
        board.placePiece(new Pawn("white", new Position(2, 3)), new Position(2, 3)); // D3
        board.placePiece(new Pawn("white", new Position(2, 5)), new Position(2, 5)); // F3

        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertFalse(whiteMoves.contains(new Position(2, 3)), "Bauer sollte keine eigene Figur auf D3 schlagen können.");
        assertFalse(whiteMoves.contains(new Position(2, 5)), "Bauer sollte keine eigene Figur auf F3 schlagen können.");
    }


    @Test
    void testInvalidBackwardMove() {
        board.printBoard();
        assertFalse(whitePawn.getValidMoves(board).contains(new Position(0, 4)), "Bauer darf nicht rückwärts ziehen.");
    }

    @Test
    void testPathBlocked() {
        board.placePiece(new Pawn("white", new Position(2, 4)), new Position(2, 4));

        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertFalse(whiteMoves.contains(new Position(3, 4)), "Bauer sollte nicht zwei Felder ziehen können, wenn der Weg blockiert ist.");
        assertFalse(whiteMoves.contains(new Position(2, 4)), "Bauer sollte nicht auf ein besetztes Feld ziehen können.");
    }
    @Test
    void testKingSafety() {
        board.placePiece(new Knight("black", new Position(2, 3)), new Position(2, 3));

        List<Position> validMoves = whitePawn.getValidMoves(board);
        assertFalse(validMoves.contains(new Position(2, 4)), "Bauer sollte keinen Zug machen, der den König im Schach lässt.");
        assertTrue(validMoves.contains(new Position(2, 3)), "Bauer sollte den schwarzen Bauer diagonal schlagen können.");
    }


    @Test
    public void testKingSafetyNoMovesAllowed() {
        board.placePiece(new Pawn("black", new Position(1, 3)), new Position(1, 3));

        List<Position> whiteMoves = whitePawn.getValidMoves(board);
        assertTrue(whiteMoves.isEmpty(), "Bauer sollte keinen Zug machen, wenn der König im Schach steht.");
    }

    @Test
    void testEnPassant() {
        Pawn whitePawn = new Pawn("white", new Position(4,3));
        board.placePiece(whitePawn, new Position(4,3));
        board.printBoard();

        boolean moveOk = board.movePiece(new Position(6,4), new Position(4,4), "black"); //schwarzer Pawn zieht 2 Felder
        assertTrue(moveOk, "Der schwarzen Bauer sollte zwei Felder gezogen sein.");

        List<Position> validMoves = whitePawn.getValidMoves(board);
        assertTrue(validMoves.contains(new Position(5,4)),
                "Der weiße Bauer sollte f5 (en passant) als gültigen Zug haben.");
    }

    @Test
    void testPawnPromotionWhite() {
        Pawn whitePawn = new Pawn("white", new Position(6,1)); //Bauer in die vorletzte Reihe
        board.placePiece(whitePawn, new Position(6,1));

        boolean moved = board.movePiece(new Position(6,1), new Position(7,1), "white");
        board.printBoard();

        assertTrue(moved, "Der Zug des weißen Bauern nach b8 sollte erfolgreich sein.");

        AbstractChessPiece piece = board.getPieceAt(new Position(7,1));
        assertNotNull(piece, "Nach dem Zug muss eine Figur am Ziel stehen.");
        assertInstanceOf(Queen.class, piece, "Der weiße Bauer sollte in eine Dame umgewandelt werden.");
    }

    @Test
    void testPawnPromotionBlack() {
        Pawn whitePawn = new Pawn("black", new Position(1,1)); //Bauer in die vorletzte Reihe
        board.placePiece(whitePawn, new Position(1,1));

        boolean moved = board.movePiece(new Position(1,1), new Position(0,1), "black");
        board.printBoard();

        assertTrue(moved, "Der Zug des schwarzen Bauern nach b0 sollte erfolgreich sein.");

        AbstractChessPiece piece = board.getPieceAt(new Position(0,1));
        assertNotNull(piece, "Nach dem Zug muss eine Figur am Ziel stehen.");
        assertInstanceOf(Queen.class, piece, "Der weiße Bauer sollte in eine Dame umgewandelt werden.");
    }
}
