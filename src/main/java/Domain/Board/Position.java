package Domain.Board;

import Domain.Exceptions.InvalidPositionFormatException;

//converted to record class
public record Position(int row, int col) {

    public Position {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            throw new InvalidPositionFormatException("Ungültige Position: " + row + ", " + col);
        }
    }

    public static boolean isWithinBounds(int row, int col) {
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

    public boolean equals(Position otherPiece) {
        return this.row == otherPiece.row && this.col == otherPiece.col;
    }

    @Override
    public String toString() {
        char letter = (char) ('a' + col);
        int number = row + 1;
        return "" + letter + number;
    }

    public static Position fromString(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Ungültiges Positionsformat: null");
        }
        s = s.trim().toLowerCase();
        if (s.length() != 2) {
            throw new IllegalArgumentException("Ungültiges Positionsformat: " + s);
        }
        char colChar = s.charAt(0);
        char rowChar = s.charAt(1);

        if (colChar < 'a' || colChar > 'h') {
            throw new IllegalArgumentException("Ungültige Spalte: " + colChar);
        }
        if (rowChar < '1' || rowChar > '8') {
            throw new IllegalArgumentException("Ungültige Reihe: " + rowChar);
        }

        int col = colChar - 'a';
        int row = rowChar - '1';

        return new Position(row, col);
    }


}
