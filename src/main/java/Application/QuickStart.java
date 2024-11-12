package Application;

import Domain.Game.Game;
import Domain.Player.HumanPlayer;
import Domain.Player.Player;

public class QuickStart {

    public static void main(String[] args) {
        // Erstelle die beiden menschlichen Spieler
        Player whitePlayer = new HumanPlayer("A", "white");
        Player blackPlayer = new HumanPlayer("B", "white");

        // Erstelle den Controller und das Spiel
        Controller controller = new CLIController();
        Game game = new Game(whitePlayer, blackPlayer, controller);

        // Starte das Spiel direkt
        game.startGame();
    }
}
