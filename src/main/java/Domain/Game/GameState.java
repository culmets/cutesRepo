package Domain.Game;

import java.util.Objects;

public record GameState(
        String activePlayer,
        String boardState,
        String moveHistory,
        String activePlayerName,
        String otherPlayerName
) {
    public static GameState fromString(String data) {
        String[] parts = data.split(";", 5);
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid game state data: " + data);
        }
        return new GameState(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim()
        );
    }

    @Override
    public String toString() {
        return activePlayer + ";" + boardState + ";" + moveHistory + ";" + activePlayerName + ";" + otherPlayerName;
    }

    public static class Builder {
        private String activePlayer;
        private String boardState;
        private String moveHistory;
        private String activePlayerName;
        private String otherPlayerName;

        public Builder activePlayer(String activePlayer) {
            this.activePlayer = activePlayer;
            return this;
        }

        public Builder boardState(String boardState) {
            this.boardState = boardState;
            return this;
        }

        public Builder moveHistory(String moveHistory) {
            this.moveHistory = moveHistory;
            return this;
        }

        public Builder activePlayerName(String activePlayerName) {
            this.activePlayerName = activePlayerName;
            return this;
        }

        public Builder otherPlayerName(String otherPlayerName) {
            this.otherPlayerName = otherPlayerName;
            return this;
        }

        public GameState build() {
            Objects.requireNonNull(activePlayer, "activePlayer must not be null");
            Objects.requireNonNull(boardState, "boardState must not be null");
            Objects.requireNonNull(moveHistory, "moveHistory must not be null");
            Objects.requireNonNull(activePlayerName, "activePlayerName must not be null");
            Objects.requireNonNull(otherPlayerName, "otherPlayerName must not be null");
            return new GameState(activePlayer, boardState, moveHistory, activePlayerName, otherPlayerName);
        }
    }
}
