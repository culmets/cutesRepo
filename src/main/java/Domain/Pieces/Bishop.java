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
    public boolean isMoveValid(Position moveFrom, Position moveTo, Board board) {
        return false;
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        int[][] directions = {
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
                if (board.isPathClear(getPosition(), newPosition)) {
                    AbstractChessPiece pieceAtNewPosition = board.getPieceAt(newPosition);
                    if (pieceAtNewPosition == null) {
                        validMoves.add(newPosition);
                    } else if (!pieceAtNewPosition.getColor().equals(this.getColor())) {
                        validMoves.add(newPosition);
                        break;
                    } else {
                        break;
                    }
                } else {
                    break;
                }

            }
        }
        validMoves.removeIf(move -> !board.isKingSafeAfterMove(this.getPosition(), move, this.getColor()));
        return validMoves;
    }
}
