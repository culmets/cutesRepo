package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

public class Queen extends AbstractChessPiece implements ChessPiece{

    public Queen(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " Q" : " q";
    }

    @Override
    public boolean isMoveValid(Position moveFrom, Position moveTo, Board board) {
        return false;
    }
}
