package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

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
        return false;
    }
}
