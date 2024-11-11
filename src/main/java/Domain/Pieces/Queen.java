package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.ArrayList;
import java.util.List;

public class Queen extends AbstractChessPiece implements ChessPiece{

    public Queen(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " Q" : " q";
    }

    @Override
    public boolean isMoveValid(Position moveFrom, Position moveTo, Board board) {
        return false;
    }

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
            int row = getPosition().row();
            int col = getPosition().col();

            while (true) {
                row += direction[0];
                col += direction[1];
                Position newPosition = new Position(row, col);
                if (!board.isWithinBoard(newPosition)) {
                    break;
                }
                AbstractChessPiece pieceAtNewPosition = board.getPieceAt(newPosition);
                if (pieceAtNewPosition != null) {
                    if (!pieceAtNewPosition.getColor().equals(this.getColor())) {
                        validMoves.add(newPosition);
                    }
                    break;
                }
                validMoves.add(newPosition);
            }
        }
        validMoves.removeIf(move -> !board.isKingSafeAfterMove(this.getPosition(), move, this.getColor()));
        return validMoves;
    }

}
