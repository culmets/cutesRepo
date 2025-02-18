package Domain.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveHistory {

    private final List<Move> moves;

    public MoveHistory() {
        moves = new ArrayList<>();
    }

    public void addMove(Move move) {
        moves.add(move);
    }

    public List<Move> getDemMoves() {
        return Collections.unmodifiableList(moves);
    }

    public int size() {
        return moves.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Move move : moves) {
            sb.append(move.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

}
