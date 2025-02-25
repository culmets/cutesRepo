package Domain.Board;

import Domain.Exceptions.KingNotFoundException;
import Domain.Pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(true); // Leeres Board
    }

    @Test
    void testPlacePieceAndGetPiece() {
        AbstractChessPiece whitePawn = new Pawn("white", new Position(1, 1));
        board.placePiece(whitePawn, new Position(1, 1));

        AbstractChessPiece piece = board.getPieceAt(new Position(1, 1));
        assertNotNull(piece, "Das Feld sollte ein weißer Bauer enthalten.");
        assertEquals("white", piece.getColor(), "Die Figur sollte weiß sein.");
        assertInstanceOf(Pawn.class, piece, "Die Figur sollte ein Bauer sein.");
    }

    @Test
    void testMovePieceValid() {
        AbstractChessPiece whitePawn = new Pawn("white", new Position(1, 1));
        AbstractChessPiece whiteKing = new King("white", new Position(7, 7));
        board.placePiece(whitePawn, new Position(1, 1));
        board.placePiece(whiteKing, new Position(7, 7));

        boolean moveSuccessful = board.movePiece(new Position(1, 1), new Position(2, 1), "white");
        assertTrue(moveSuccessful, "Der Zug sollte erfolgreich sein.");
        assertNull(board.getPieceAt(new Position(1, 1)), "Das Startfeld sollte leer sein.");
        assertNotNull(board.getPieceAt(new Position(2, 1)), "Das Zielfeld sollte den weißen Bauer enthalten.");
    }

    @Test
    void testMovePieceInvalid() {
        AbstractChessPiece whitePawn = new Pawn("white", new Position(1, 1));
        AbstractChessPiece whiteKing = new King("white", new Position(7, 7));
        board.placePiece(whitePawn, new Position(1, 1));
        board.placePiece(whiteKing, new Position(7, 7));

        boolean moveSuccessful = board.movePiece(new Position(1, 1), new Position(4, 1), "white");
        assertFalse(moveSuccessful, "Der Zug sollte nicht erfolgreich sein.");
        assertNotNull(board.getPieceAt(new Position(1, 1)), "Das Startfeld sollte weiterhin den weißen Bauer enthalten.");
        assertNull(board.getPieceAt(new Position(3, 1)), "Das Zielfeld sollte leer sein.");
    }

    @Test
    void testKingInCheck() {
        AbstractChessPiece whiteKing = new King("white", new Position(0, 0));
        AbstractChessPiece blackRook = new Rook("black", new Position(0, 7));
        board.placePiece(whiteKing, new Position(0, 0));
        board.placePiece(blackRook, new Position(0, 7));

        assertTrue(board.isKingInCheck("white"), "Der weiße König sollte im Schach stehen.");

        board.movePiece(new Position(0, 0), new Position(1, 0), "white");
        assertFalse(board.isKingInCheck("white"), "Der weiße König sollte nicht mehr im Schach stehen.");
    }

    @Test
    void testKingNotInCheck() {
        AbstractChessPiece whiteKing = new King("white", new Position(0, 0));
        AbstractChessPiece blackRook = new Rook("black", new Position(1, 7));
        board.placePiece(whiteKing, new Position(0, 0));
        board.placePiece(blackRook, new Position(1, 7));

        assertFalse(board.isKingInCheck("white"), "Der weiße König sollte nicht im Schach stehen.");
    }

    @Test
    void testIsCheckmate() {
        AbstractChessPiece whiteKing = new King("white", new Position(0, 0));
        AbstractChessPiece blackRook1 = new Rook("black", new Position(0, 7));
        AbstractChessPiece blackRook2 = new Rook("black", new Position(7, 0));
        AbstractChessPiece blackQueen = new Queen("black", new Position(7, 7));
        board.placePiece(whiteKing, new Position(0, 0));
        board.placePiece(blackRook1, new Position(0, 7));
        board.placePiece(blackRook2, new Position(7, 0));
        board.placePiece(blackQueen, new Position(7, 7));
        board.printBoard();
        assertTrue(board.isCheckmate("white"), "Der weiße König sollte Schachmatt sein.");
    }

    @Test
    void testIsStalemate() {
        AbstractChessPiece whiteKing = new King("white", new Position(7, 5));
        AbstractChessPiece blackKing = new King("black", new Position(5, 5));
        AbstractChessPiece blackPawn = new Pawn("black", new Position(6, 5));
        board.placePiece(whiteKing, new Position(7, 5));
        board.placePiece(blackKing, new Position(5, 5));
        board.placePiece(blackPawn, new Position(6, 5));
        board.printBoard();

        assertTrue(board.isStalemate("white"), "Der weiße König sollte Patt sein.");
    }

    @Test
    void isStalemate2(){
        AbstractChessPiece whiteKing = new King("white", new Position(5, 1));
        AbstractChessPiece blackKing = new King("black", new Position(7, 0));
        AbstractChessPiece blackRook = new Knight("white", new Position(5, 2));
        board.placePiece(whiteKing, new Position(5, 1));
        board.placePiece(blackKing, new Position(7, 0));
        board.placePiece(blackRook, new Position(5, 2));
        board.printBoard();

        assertTrue(board.isStalemate("black"), "Der weiße König sollte Patt sein.");

    }

    @Test
    void testPathClear() {
        board.placePiece(new Rook("white", new Position(0, 0)), new Position(0, 0));
        board.placePiece(new Pawn("white", new Position(0, 4)), new Position(0, 4));

        assertFalse(board.isPathClear(new Position(0, 0), new Position(0, 7)), "Der Pfad sollte nicht frei sein.");
        assertTrue(board.isPathClear(new Position(0, 0), new Position(0, 3)), "Der Pfad sollte frei sein.");
    }

    @Test
    void testPositionConversion() {
        Position pos = new Position(7, 5);  // Erwartet "f8"
        assertEquals("f8", pos.toString());
        Position pos2 = Position.fromString("f8");
        assertEquals(pos, pos2);
    }
}
