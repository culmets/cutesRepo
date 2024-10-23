package Domain;


public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new IllegalArgumentException("Ungültige Position: " + x + ", " + y);
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Position otherPiece){
        return this.x == otherPiece.x && this.y == otherPiece.y;
    }

    @Override
    public String toString() {
        char column = (char) ('a' + x);
        int row = y + 1;
        return "" + column + row;
    }
}
