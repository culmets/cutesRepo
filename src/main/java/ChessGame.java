
import  java.util.Scanner;

public class ChessGame {

    private Board board;
    private boolean whitesTurn;
    private Scanner scanner;

    public ChessGame(Board board, Scanner scanner, boolean whitesTurn) {
        this.board = board;
        this.scanner = scanner;
        this.whitesTurn = whitesTurn;
    }
}
