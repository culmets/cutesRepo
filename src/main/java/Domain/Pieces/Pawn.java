package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

public class Pawn extends AbstractChessPiece implements ChessPiece {
    public Pawn(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isMoveValid(Position moveFRom, Position moveTo, Board board) {
        return false;
    }
    /*
        @Override
        public List<Position> getValidMoves(Board board) {
            return List.of();
        }
    */
    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " P" : " p";
    }
}
