package Domain.Game;

import Domain.Board.Position;

public record Move(Position from, Position to, int moveNumber, MoveType moveType) {

    @Override
    public String toString() {
        // Beispiel: "e2,e4,1,normal"
        return from.toString() + "," + to.toString() + "," + moveNumber + "," + moveType.toString();
    }
    public static Move fromString(String s) {
        String[] parts = s.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid move format: " + s);
        }
        Position start = Position.fromString(parts[0].trim());
        Position end = Position.fromString(parts[1].trim());
        int moveNumber = Integer.parseInt(parts[2].trim());
        MoveType moveType = MoveType.valueOf(parts[3].trim().toUpperCase());
        return new Move(start, end, moveNumber, moveType);
    }
}

