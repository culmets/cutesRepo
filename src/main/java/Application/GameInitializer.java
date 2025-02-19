package Application;

import Domain.Board.Board;
import Domain.Game.Game;
import Domain.Game.MoveHistory;
import Domain.Persistence.GameRecordRepo;
import Domain.Player.ComputerPlayer;
import Domain.Player.HumanPlayer;
import Domain.Player.Player;
import Persistence.FileGameRecordRepo;

import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GameInitializer {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        String basePath = System.getProperty("user.dir") + File.separator + "saved_games";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Schach");
        System.out.println("Wählen Sie den Spielmodus:");
        System.out.println("1 - Zwei menschliche Spieler");
        System.out.println("2 - Mensch gegen Computer");

        int choice;
        while (true) {
            System.out.print("Eingabe (1 oder 2): ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1 || choice == 2) {
                break;
            } else {
                System.out.println("Ungültige Eingabe. Bitte geben Sie 1 oder 2 ein.");
            }
        }
        Player whitePlayer, blackPlayer;

        System.out.print("Name des weißen Spielers: ");
        String whiteName = scanner.nextLine();
        whitePlayer = new HumanPlayer(whiteName, "white");

        if (choice == 1) {
            System.out.print("Name des schwarzen Spielers: ");
            String blackName = scanner.nextLine();
            blackPlayer = new HumanPlayer(blackName, "black");
        } else {
            blackPlayer = new ComputerPlayer("Computer (Schwarz)", "black");
        }

        Controller controller = new CLIController();

        GameRecordRepo repository = new FileGameRecordRepo(basePath);
        MoveHistory moveHistory = new MoveHistory();
        Board board = new Board();

        Game game = new Game(whitePlayer, blackPlayer, controller, moveHistory, board, repository);

        game.startGame();
    }
}
