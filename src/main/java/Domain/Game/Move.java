package Domain.Game;

import Domain.Board.Position;

public record Move(Position from, Position to, int moveNumber, MoveType moveType) {

    @Override
    public String toString() {
        // Beispiel: "e2,e4,1,normal"
        return from.toString() + "," + to.toString() + "," + moveNumber + "," + moveType.toString();
    }
}

