package Application;

import Domain.Board.Position;

import java.util.Scanner;

public class CLIController implements Controller{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void startGame() {
        System.out.println("Willkommen zum Schachspiel!");
    }

    @Override
    public void endGame() {
        System.out.println("Spiel beendet. Danke fürs Spielen!");
    }

    @Override
    public Position getMoveStart() {
        System.out.print("Geben Sie die Startposition ein (z. B. e2): ");
        String input = scanner.nextLine();
        return parsePosition(input);
    }

    @Override
    public Position getMoveEnd() {
        System.out.print("Geben Sie die Zielposition ein (z. B. e4): ");
        String input = scanner.nextLine();
        return parsePosition(input);
    }

    private Position parsePosition(String input) {
        int col = input.charAt(0) - 'a'; //zieht a (97) von anderem char ab
        int row = Character.getNumericValue(input.charAt(1)) - 1; // zahl minus 1 weil von 0 gezählt wird
        return new Position(row, col);
    }

    @Override
    public void computeMove() {

    }

    @Override
    public void saveGame() {

    }

    @Override
    public void loadGame() {

    }


}
