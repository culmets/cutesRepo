package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractChessPiece implements ChessPiece{
    public Rook(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " R" : " r";
    }

    @Override
    public boolean isMoveValid(Position moveFrom, Position moveTo, Board board) {
        return getValidMoves(board).contains(moveTo);
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        int[][] moves = {
                {1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0},
                {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}
        };

        for (int[] move : moves) {
            int newRow = getPosition().row() + move[0];
            int newCol = getPosition().col() + move[1];
            Position newPosition = new Position(newRow, newCol);
//ist die neue Position noch aufm board und steht wenn dann ein gegnerisches Piece darauf
            if (board.isWithinBoard(newPosition) && (board.getPieceAt(newPosition) == null ||
                    !board.getPieceAt(newPosition).getColor().equals(this.getColor()))) {
                // wenn zwischen der figur und dem Ziel noch etwas steht -> nicht durchlaufen
                if(board.isPathFree(getPosition(), newPosition)){
                    validMoves.add(newPosition);
                }
            }
        }
        return validMoves;
    }
}
