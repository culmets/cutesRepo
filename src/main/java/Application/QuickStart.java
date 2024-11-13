package Application;

import Domain.Game.Game;
import Domain.Player.HumanPlayer;
import Domain.Player.Player;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class QuickStart {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Player whitePlayer = new HumanPlayer("A", "white");
        Player blackPlayer = new HumanPlayer("B", "black");

        Controller controller = new CLIController();
        Game game = new Game(whitePlayer, blackPlayer, controller);

        game.startGame();
    }
}
