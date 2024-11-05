package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

public class Knight extends AbstractChessPiece implements ChessPiece{
    public Knight(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " K" : " k";
    }

    @Override
    public boolean isMoveValid(Position moveFrom, Position moveTo, Board board) {
        return false;
    }
}
