package Domain.Board;

import Domain.Pieces.*;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private Map<Position, AbstractChessPiece> board;

    public Board() {
        this.board = new HashMap<>();
        initializePieces();
    }

    private void initializePieces() {
        board.put(new Position(0, 0), new Rook("white", new Position(0, 0)));
        board.put(new Position(0, 1), new Knight("white", new Position(0, 1)));
        board.put(new Position(0, 2), new Bishop("white", new Position(0, 2)));
        board.put(new Position(0, 3), new Queen("white", new Position(0, 3)));
        board.put(new Position(0, 4), new King("white", new Position(0, 4)));
        board.put(new Position(0, 5), new Bishop("white", new Position(0, 5)));
        board.put(new Position(0, 6), new Knight("white", new Position(0, 6)));
        board.put(new Position(0, 7), new Rook("white", new Position(0, 7)));

        for (int col = 0; col < 8; col++) {
            board.put(new Position(1, col), new Pawn("white", new Position(1, col)));
        }

        board.put(new Position(7, 0), new Rook("black", new Position(7, 0)));
        board.put(new Position(7, 1), new Knight("black", new Position(7, 1)));
        board.put(new Position(7, 2), new Bishop("black", new Position(7, 2)));
        board.put(new Position(7, 3), new Queen("black", new Position(7, 3)));
        board.put(new Position(7, 4), new King("black", new Position(7, 4)));
        board.put(new Position(7, 5), new Bishop("black", new Position(7, 5)));
        board.put(new Position(7, 6), new Knight("black", new Position(7, 6)));
        board.put(new Position(7, 7), new Rook("black", new Position(7, 7)));

        for (int col = 0; col < 8; col++) {
            board.put(new Position(6, col), new Pawn("black", new Position(6, col)));
        }
    }


    public AbstractChessPiece getPieceAt(Position position) {
        return board.get(position);
    }

    public boolean movePiece(Position start, Position end) {
        AbstractChessPiece piece = board.get(start);

        if (piece == null) {
            System.out.println("No piece at the start position.");
            return false;
        }

        if (!piece.isMoveValid(start, end, this)) {
            System.out.println("Invalid move for " + piece.getClass().getSimpleName());
            return false;
        }

        board.put(end, piece);
        board.remove(start);
        piece.setPosition(end);
        return true;
    }

    public void printBoard() {
        System.out.println("   A  B  C  D  E  F  G  H");
        System.out.println(" +------------------------+");

        for (int row = 7; row >= 0; row--) {
            System.out.print((row + 1) + "| ");

            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                AbstractChessPiece piece = board.get(pos);
                if (piece == null) {
                    System.out.print(" . ");
                } else {
                    System.out.print(piece.getSymbol() + " ");
                }
            }
            System.out.println("| " + (row + 1));
        }
        System.out.println(" +------------------------+");
        System.out.println("   A  B  C  D  E  F  G  H");
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();
    }
}
