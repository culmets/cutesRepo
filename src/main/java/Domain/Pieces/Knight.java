package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.ArrayList;
import java.util.Collections;
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
        int[][] directions = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] direction : directions) {
            validMoves.addAll(computeDirectionalMoves(board, direction[0], direction[1]));
        }
        return validMoves;
    }

    private List<Position> computeDirectionalMoves(Board board, int rowDir, int colDir) {
        List<Position> moves = new ArrayList<>();
        int row = getPosition().row() + rowDir;
        int col = getPosition().col() + colDir;

        if (!Position.isWithinBounds(row, col)) {
            return Collections.emptyList();
        }
        Position newPosition = new Position(row, col);

        AbstractChessPiece pieceAtNewPosition = board.getPieceAt(newPosition);
        if (pieceAtNewPosition == null || !pieceAtNewPosition.getColor().equals(this.getColor())) {
            if (board.isKingSafeAfterMove(getPosition(), newPosition, getColor())) {
                moves.add(newPosition);
            }
        }
        return moves;
    }
}
