package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends AbstractChessPiece implements ChessPiece {
    public Bishop(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " B" : " b";
    }

    @Override
    public boolean canAttack(Position targetPosition, Board board) {
        int rowDiff = Math.abs(targetPosition.row() - getPosition().row());
        int colDiff = Math.abs(targetPosition.col() - getPosition().col());

        if (rowDiff == colDiff) {
            return board.isPathClear(getPosition(), targetPosition);
        }
        return false;
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

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

                if (!board.isPathClear(getPosition(), newPosition)) {
                    break;
                }
                AbstractChessPiece pieceAtNewPosition = board.getPieceAt(newPosition);

                if (pieceAtNewPosition == null || !pieceAtNewPosition.getColor().equals(this.getColor())) {
                    if (board.isKingSafeAfterMove(this.getPosition(), newPosition, this.getColor())) {
                        validMoves.add(newPosition);
                    }
                }
            }
        }
        return validMoves;
    }
}
