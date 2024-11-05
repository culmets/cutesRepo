package Domain;

import java.util.List;

public interface ChessPiece {

    boolean moveIsValid(Position moveTo, Board board);

    List<Position> getValidMoves(Board board);

}
