package Application;

import Application.Command.GameCommand;

public interface Controller {

    void startGame();
    void endGame();
    GameCommand getCommand();
}
