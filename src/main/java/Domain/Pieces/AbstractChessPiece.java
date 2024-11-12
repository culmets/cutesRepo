package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

public abstract class AbstractChessPiece implements ChessPiece {

    private final String color;

    private Position position;

    public AbstractChessPiece(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    public abstract String getSymbol();

    public abstract  boolean canAttack(Position targetPosition, Board board);

}
