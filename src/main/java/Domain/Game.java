package Domain;

public interface Game {

    void startGame();

    void makeMove(Position from, Position to);

    boolean isGameOver();

    String getWinner();
}
