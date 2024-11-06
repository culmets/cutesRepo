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
        return (getColor().equals("white")) ? " K" : " k";
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
            int newRow = getPosition().row() + move[0];
            int newCol = getPosition().col() + move[1];
            Position newPosition = new Position(newRow, newCol);
//ist die neue Position noch aufm board und steht wenn dann ein gegnerisches Piece darauf
            if (board.isWithinBoard(newPosition) && (board.getPieceAt(newPosition) == null ||
                    !board.getPieceAt(newPosition).getColor().equals(this.getColor()))) {
                validMoves.add(newPosition);
            }
        }
        return validMoves;
    }
}
