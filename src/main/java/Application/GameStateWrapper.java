package Application;

import Domain.Game.GameState;

import java.util.Optional;

public record GameStateWrapper(String fileName, Optional<GameState> gameState) {

}
