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

    @Override //wenn moveTo (endposition der figur) in der liste enthalten ist, ist der Zug valid
    public boolean isMoveValid(Position moveFrom, Position moveTo, Board board) {
        return getValidMoves(board).contains(moveTo);
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
