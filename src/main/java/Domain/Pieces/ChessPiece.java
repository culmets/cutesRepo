package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.List;

public interface ChessPiece {

    //kann man rausschmeissen?
    boolean isMoveValid(Position moveFrom, Position moveTo, Board board);

    List<Position> getValidMoves(Board board);

}
