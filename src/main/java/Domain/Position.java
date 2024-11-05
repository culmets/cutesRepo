package Domain;

//converted to record class
public record Position(int x, int y) {

    public Position {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new IllegalArgumentException("Ungültige Position: " + x + ", " + y);
        }
    }

    public boolean equals(Position otherPiece) {
        return this.x == otherPiece.x && this.y == otherPiece.y;
    }

    @Override
    public String toString() {
        char column = (char) ('a' + x);
        int row = y + 1;
        return "" + column + row;
    }
}
