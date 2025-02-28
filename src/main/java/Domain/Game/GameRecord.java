package Domain.Game;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @param winner "white", "black" oder "draw"
 */
public record GameRecord(String whitePlayer, String blackPlayer, String winner, List<Move> moveHistory,
                         LocalDateTime gameDate) {

    @Override
    public String toString() {
        return "White: " + whitePlayer + "\n" +
                "Black: " + blackPlayer + "\n" +
                "Winner: " + winner + "\n" +
                "Date: " + gameDate + "\n" +
                "Moves:\n" + moveHistory.toString();
    }

}
