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
    public boolean canAttack(Position targetPosition, Board board) {
        int rowDiff = Math.abs(targetPosition.row() - getPosition().row());
        int colDiff = Math.abs(targetPosition.col() - getPosition().col());

        return rowDiff <= 1 && colDiff <= 1;
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

            if (row < 0 || row > 7 || col < 0 || col > 7) {
                continue;
            }
            Position newPosition = new Position(row, col);

            AbstractChessPiece pieceAtNewPosition = board.getPieceAt(newPosition);

            if (pieceAtNewPosition == null || !pieceAtNewPosition.getColor().equals(this.getColor())) {
                validMoves.add(newPosition);
            }
        }
        validMoves.removeIf(move -> !board.isKingSafeAfterMove(this.getPosition(), move, this.getColor()));
        return validMoves;
    }
}
