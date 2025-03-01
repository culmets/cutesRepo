package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractChessPiece implements ChessPiece {
    public Rook(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " R" : " r";
    }

    @Override
    public boolean canAttack(Position targetPosition, Board board) {
        if (targetPosition.row() == getPosition().row() || targetPosition.col() == getPosition().col()) {
            return board.isPathClear(getPosition(), targetPosition);
        }
        return false;
    }

    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        for (int[] direction : directions) {
            int row = getPosition().row();
            int col = getPosition().col();
            while (true) {
                row += direction[0];
                col += direction[1];

                if (!Position.isWithinBounds(row, col)) {
                    break;
                }

                Position newPosition = new Position(row, col);

                AbstractChessPiece pieceAtNewPosition = board.getPieceAt(newPosition);

                if (pieceAtNewPosition == null || pieceAtNewPosition.getColor().equals(this.getColor())) {
                   if (board.isKingSafeAfterMove(this.getPosition(), newPosition, this.getColor())) {
                       validMoves.add(newPosition);
                   }
                }
            }
        }
        return validMoves;
    }
}
