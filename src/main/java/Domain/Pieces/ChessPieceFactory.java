package Domain.Pieces;

import Domain.Board.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ChessPieceFactory {
    private static final Map<String, BiFunction<String, Position, AbstractChessPiece>> creators = new HashMap<>();

    static {
        creators.put("pawn", Pawn::new);
        creators.put("knight", Knight::new);
        creators.put("bishop", Bishop::new);
        creators.put("rook", Rook::new);
        creators.put("queen", Queen::new);
        creators.put("king", King::new);
    }

    public static AbstractChessPiece createPiece(String pieceType, String color, Position pos) {
        BiFunction<String, Position, AbstractChessPiece> creator = creators.get(pieceType.toLowerCase());
        return (creator != null) ? creator.apply(color, pos) : null;
    }
}
