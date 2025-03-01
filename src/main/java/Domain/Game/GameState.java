package Domain.Game;

import java.time.LocalDateTime;
import java.util.Arrays;

public record GameState(String activePlayer, String boardState, String moveHistory, String activePlayerName, String otherPlayerName) {

    public static GameState fromString(String data) {
        //Format: activePlayer;boardState;moveHistory;name;name
        String[] parts = data.split(";", 5);
        if (parts.length < 5) {
            System.out.println(Arrays.toString(parts));
            throw new IllegalArgumentException("Invalid game state data: " + data);
        }
        String activePlayer = parts[0];
        String boardState = parts[1];
        String moveHistory = parts[2];
        String activePlayerName = parts[3];
        String otherPlayerName = parts[4];
        return new GameState(activePlayer, boardState, moveHistory, activePlayerName, otherPlayerName);
    }

    @Override
    public String toString() {
        // Format: white;dummyBoard;dummyHistory;name;name
        return activePlayer + ";" + boardState + ";" + moveHistory + ";" + activePlayerName + ";" + otherPlayerName;
    }


}
