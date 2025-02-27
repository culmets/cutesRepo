package Domain.Game;

import java.time.LocalDateTime;
import java.util.Arrays;

public record GameState(String activePlayer, String boardState, String moveHistory, LocalDateTime timestamp) {

    public static GameState fromString(String data) {
        //Format: activePlayer;boardState;moveHistory;timestamp
        String[] parts = data.split(";", 4);
        if (parts.length < 4) {
            System.out.println(Arrays.toString(parts));
            throw new IllegalArgumentException("Invalid game state data: " + data);
        }
        String activePlayer = parts[0];
        String boardState = parts[1];
        String moveHistory = parts[2];
        LocalDateTime timestamp = LocalDateTime.now();
        return new GameState(activePlayer, boardState, moveHistory, timestamp);
    }

    @Override
    public String toString() {
        // z. B.: "white;dummyBoard;dummyHistory;2023-03-01T12:00:00"
        return activePlayer + ";" + boardState + ";" + moveHistory + ";" + timestamp;
    }


}
