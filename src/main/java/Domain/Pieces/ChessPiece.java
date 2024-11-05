package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

public interface ChessPiece {

    boolean isMoveValid(Position moveFrom, Position moveTo, Board board);

  // nohcmal überlegen wie machen
  //  List<Position> getValidMoves(Board board);
  // lohnt es sich immer alles vor zu berechnen?
}
