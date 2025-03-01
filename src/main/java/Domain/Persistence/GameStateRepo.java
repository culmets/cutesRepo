package Domain.Persistence;

import Domain.Game.GameState;

import java.util.Optional;

public interface GameStateRepo {
        String saveGameState(GameState state);
        Optional<GameState> loadGameState(String fileName);
        void deleteGameState(String fileName);
    }

