package Domain.Game;

import Domain.Board.Position;

public record Move(Position from, Position to, int moveNumber) {

    @Override
    public String toString() {
        // Format: e2,e4,1
        return from.toString() + "," + to.toString() + "," + moveNumber;
    }
    public static Move fromString(String s) {
        String[] parts = s.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid move format: " + s);
        }
        Position start = Position.fromString(parts[0].trim());
        Position end = Position.fromString(parts[1].trim());
        int moveNumber = Integer.parseInt(parts[2].trim());
        return new Move(start, end, moveNumber);
    }
}

