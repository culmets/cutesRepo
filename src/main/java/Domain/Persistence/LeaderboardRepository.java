package Domain.Persistence;

import Application.LeaderboardEntry;

import java.util.List;

public interface LeaderboardRepository {
    void updateWin(String playerName);
    List<LeaderboardEntry> getLeaderboard();
    void printLeaderboard();
}
