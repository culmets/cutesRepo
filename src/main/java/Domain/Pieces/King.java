package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.ArrayList;
import java.util.List;

public class King extends AbstractChessPiece implements ChessPiece{
    public King(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " K" : " k";
    }

    @Override
    public boolean isMoveValid(Position moveFrom, Position moveTo, Board board) {
        return false;
    }

    //rochade implementieren

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1},
                {1, 1},
                {1, -1},
                {-1, 1},
                {-1, -1}
        };

        for (int[] direction : directions) {
            int row = getPosition().row() + direction[0];
            int col = getPosition().col() + direction[1];
            Position newPosition = new Position(row, col);

            if (board.isWithinBoard(newPosition)) {
                AbstractChessPiece pieceAtNewPosition = board.getPieceAt(newPosition);
                if (pieceAtNewPosition == null || !pieceAtNewPosition.getColor().equals(this.getColor())) {
                    validMoves.add(newPosition);
                }
            }
        }
        validMoves.removeIf(move -> !board.isKingSafeAfterMove(this.getPosition(), move, this.getColor()));
        return validMoves;
    }
}
