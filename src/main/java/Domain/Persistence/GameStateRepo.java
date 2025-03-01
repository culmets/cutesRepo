package Domain.Persistence;

import Domain.Game.GameState;

import java.util.List;
import java.util.Optional;

public interface GameStateRepo {
        String saveGameState(GameState state);
        Optional<GameState> loadGameState(String fileName);
        void deleteGameState(String fileName);
        List<String> listGameStateFileNames();
}

