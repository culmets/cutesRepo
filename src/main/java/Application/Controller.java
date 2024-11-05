package Application;

import Domain.Board.Position;

public interface Controller {

    void startGame();
    void endGame();
    Position getMoveStart();
    Position getMoveEnd();

    void computeMove();
    void saveGame();
    void loadGame();
}
