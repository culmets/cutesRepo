package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.List;

public interface ChessPiece {
    List<Position> getValidMoves(Board board);
}
