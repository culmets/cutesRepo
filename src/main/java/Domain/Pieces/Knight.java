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
            int newRow = getPosition().row() + move[0];
            int newCol = getPosition().col() + move[1];

            if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
                break;
            }
            Position newPosition = new Position(newRow, newCol);
//ist die neue Position noch aufm board und steht wenn dann ein gegnerisches Piece darauf
            if (board.isWithinBoard(newPosition) && (board.getPieceAt(newPosition) == null ||
                    !board.getPieceAt(newPosition).getColor().equals(this.getColor()))) {
                validMoves.add(newPosition);
            }
        }
        validMoves.removeIf(move -> !board.isKingSafeAfterMove(this.getPosition(), move, this.getColor()));
        return validMoves;
    }
}
