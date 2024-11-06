package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.List;

public class Bishop extends AbstractChessPiece implements ChessPiece{
    public Bishop(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " B" : " b";
    }

    @Override
    public boolean isMoveValid(Position moveFrom, Position moveTo, Board board) {
        return false;
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        return List.of();
    }
}
