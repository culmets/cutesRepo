package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends AbstractChessPiece implements ChessPiece {
    public Pawn(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isMoveValid(Position moveFRom, Position moveTo, Board board) {
        return false;
    }

    //en passant noch implementieren
    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        boolean isPawnWhite = getColor().equals("white");
        int direction = isPawnWhite ? 1 : -1; // weiße pawns gehen hoch (+1), schwarze runter (-1)

        // ein feld vorwärts
        Position oneStepForward = new Position(getPosition().row() + direction, getPosition().col());
        if (board.isWithinBoard(oneStepForward) && board.getPieceAt(oneStepForward) == null) {
            validMoves.add(oneStepForward);

            // Bei startpos: zwei Felder vorwärts
            int startRow = isPawnWhite ? 1 : 6;
            Position twoStepsForward = new Position(getPosition().row() + 2 * direction, getPosition().col());
            if (getPosition().row() == startRow && board.getPieceAt(twoStepsForward) == null) {
                validMoves.add(twoStepsForward);
            }
        }

        // diagonal schlagen
        Position captureLeft = new Position(getPosition().row() + direction, getPosition().col() - 1);
        Position captureRight = new Position(getPosition().row() + direction, getPosition().col() + 1);

        if (board.isWithinBoard(captureLeft) && board.getPieceAt(captureLeft) != null &&
                !board.getPieceAt(captureLeft).getColor().equals(this.getColor())) {
            validMoves.add(captureLeft);
        }

        if (board.isWithinBoard(captureRight) && board.getPieceAt(captureRight) != null &&
                !board.getPieceAt(captureRight).getColor().equals(this.getColor())) {
            validMoves.add(captureRight);
        }
        return validMoves;
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " P" : " p";
    }
}
