package Persistence;

import Domain.Game.GameRecord;

import java.util.List;

public interface GameRecordRepo {
    void saveGameRecord(GameRecord record);
    List<GameRecord> getGameRecordsForPlayer(String playerName);
}
