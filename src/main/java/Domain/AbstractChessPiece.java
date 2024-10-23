package Domain;

public abstract class AbstractChessPiece implements ChessPiece{

    protected String name;

    protected String color;

    public AbstractChessPiece(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
