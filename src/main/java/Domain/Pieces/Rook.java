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

    @Override
    public boolean isMoveValid(Position moveFrom, Position moveTo, Board board) {
        return getValidMoves(board).contains(moveTo);
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

                if (row < 0 || row > 7 || col < 0 || col > 7) {
                    break; // verlasse schleife wenn pos außerhalb von board
                }

                Position newPosition = new Position(row, col);

                AbstractChessPiece pieceAtNewPosition = board.getPieceAt(newPosition);

                if (pieceAtNewPosition == null) {
                    validMoves.add(newPosition);
                } else {
                    if (!pieceAtNewPosition.getColor().equals(this.getColor())) {
                        validMoves.add(newPosition);
                    }
                    break;
                }
            }
        }
        //move ist endpos -> prüfung ob endpos könig in schach stellt
        validMoves.removeIf(move -> !board.isKingSafeAfterMove(this.getPosition(), move, this.getColor()));
        return validMoves;
    }
}
