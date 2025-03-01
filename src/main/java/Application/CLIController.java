package Application;

import Application.Command.GameCommand;
import Application.Command.MoveCommand;
import Application.Command.SaveCommand;
import Domain.Board.Position;

import java.util.Scanner;

public class CLIController implements Controller{
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void startGame() {
        System.out.println("Willkommen zum Schachspiel!");
        System.out.println("Großbuchstaben sind die weißen Figuren und Kleinbuchstaben die schwarzen.");
        System.out.println("'S' bzw. 's' steht für Springer (Knight)");
    }

    @Override
    public void endGame() {
        System.out.println("Spiel beendet. Danke fürs Spielen!");
    }


    @Override
    public GameCommand getCommand() {
        while (true) {
            System.out.print("Geben Sie die Startposition ein (z.B. e2 oder 'speichern'): ");
            String inputStart = scanner.nextLine().trim();
            if (inputStart.equalsIgnoreCase("speichern")) {
                return new SaveCommand();
            }
            Position start;
            try {
                start = Position.fromString(inputStart);
            } catch (IllegalArgumentException e) {
                System.out.println("Ungültige Startposition: " + e.getMessage());
                continue;
            }

            System.out.print("Geben Sie die Zielposition ein (z.B. e4): ");
            String inputEnd = scanner.nextLine().trim();

            Position end;
            try {
                end = Position.fromString(inputEnd);
            } catch (IllegalArgumentException e) {
                System.out.println("Ungültige Zielposition: " + e.getMessage());
                continue;
            }

            return new MoveCommand(start, end);
        }
    }
}
