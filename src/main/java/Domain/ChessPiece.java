package Domain;

public interface ChessPiece {

    boolean moveIsValid(Position moveFrom, Position moveTo, ChessPiece piece);



}
