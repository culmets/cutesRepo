package Domain;

public abstract class AbstractChessPiece implements ChessPiece{

    private String color;

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
}
