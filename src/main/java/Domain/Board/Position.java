package Domain.Board;

//converted to record class
public record Position(int row, int col) {

    public Position {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            throw new IllegalArgumentException("Ungültige Position: " + row + ", " + col);
        }
    }

    public boolean equals(Position otherPiece) {
        return this.row == otherPiece.row && this.col == otherPiece.col;
    }

    @Override
    public String toString() {
        char column = (char) ('a' + row);
        int row = col + 1;
        return "" + column + row;
    }
}
