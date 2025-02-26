package Application.Command;

import Domain.Board.Position;

public record MoveCommand(Position start, Position end) implements GameCommand{
}
