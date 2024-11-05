package Domain.Board;

import Domain.Pieces.AbstractChessPiece;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private Map<Position, AbstractChessPiece> board;

    public Board() {
        this.board = new HashMap<>();
        initializePieces();
    }

    private void initializePieces() {
  //      board.put(new Position(0, 0), new Rook("white", new Position(0, 0)));
   //     board.put(new Position(0, 1), new Knight("white", new Position(0, 1)));
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
}
