package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight extends AbstractChessPiece implements ChessPiece{

    public Knight(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " S" : " s";
    }

    @Override
    public boolean canAttack(Position targetPosition, Board board) {
        int rowDiff = Math.abs(targetPosition.row() - getPosition().row());
        int colDiff = Math.abs(targetPosition.col() - getPosition().col());

        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        int[][] moves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] move : moves) {
            int row = getPosition().row() + move[0];
            int col = getPosition().col() + move[1];

            if (!Position.isWithinBounds(row, col)) {
                continue;
            }

            Position newPosition = new Position(row, col);

            AbstractChessPiece pieceAtNewPosition = board.getPieceAt(newPosition);

            if (pieceAtNewPosition == null || !pieceAtNewPosition.getColor().equals(this.getColor())) {
                if(board.isKingSafeAfterMove(this.getPosition(), newPosition, this.getColor())) {
                    validMoves.add(newPosition);
                }
            }
        }
        return validMoves;
    }
}
