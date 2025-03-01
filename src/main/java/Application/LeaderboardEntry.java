package Application;

public record LeaderboardEntry(String playerName, int winCount) {

    @Override
    public String toString() {
        return "Player: " + playerName + "\n" +
                "Wins: " + winCount;
    }
}
