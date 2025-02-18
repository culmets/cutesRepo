package Persistence;

import Domain.Game.GameRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Leaderboard {

    private final List<GameRecord> gameRecords;

    public Leaderboard() {
        gameRecords = new ArrayList<>();
    }

    public void addGameRecord(GameRecord record) {
        gameRecords.add(record);
    }

    public List<GameRecord> getGameRecords() {
        return Collections.unmodifiableList(gameRecords);
    }

    public List<GameRecord> getGameRecordsForPlayer(String playerName) {
        return gameRecords.stream()
                .filter(record -> record.getWhitePlayer().equalsIgnoreCase(playerName)
                        || record.getBlackPlayer().equalsIgnoreCase(playerName))
                .collect(Collectors.toList());
    }

    public int getWinCount(String playerName) {
        return (int) gameRecords.stream()
                .filter(record -> record.getWinner().equalsIgnoreCase(playerName))
                .count();
    }

    public List<String> getRanking() {
        return gameRecords.stream()
                .flatMap(record -> Stream.of(record.getWhitePlayer(), record.getBlackPlayer()))
                .distinct()
                .sorted((player1, player2) -> Integer.compare(getWinCount(player2), getWinCount(player1)))
                .collect(Collectors.toList());
    }

}
