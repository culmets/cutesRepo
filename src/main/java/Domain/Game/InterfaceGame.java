package Domain.Game;

import Domain.Player.Player;

public interface InterfaceGame {

    void startGame();

    boolean isGameOver(Player currenPlayer);

    String getWinner();


}
