package Domain.Game;

import java.time.LocalDateTime;
import java.util.List;

public class GameRecord {

    private String whitePlayer;
    private String blackPlayer;
    private String winner; // "white", "black" oder "draw"
    private List<Move> moveHistory;
    private LocalDateTime gameDate;

    public GameRecord(String whitePlayer, String blackPlayer, String winner, List<Move> moveHistory, LocalDateTime gameDate) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.winner = winner;
        this.moveHistory = moveHistory;
        this.gameDate = gameDate;
    }

    public String getBlackPlayer() {
        return blackPlayer;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public String getWhitePlayer() {
        return whitePlayer;
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return "White: " + whitePlayer + "\n" +
                "Black: " + blackPlayer + "\n" +
                "Winner: " + winner + "\n" +
                "Date: " + gameDate + "\n" +
                "Moves:\n" + moveHistory.toString();
    }

}
