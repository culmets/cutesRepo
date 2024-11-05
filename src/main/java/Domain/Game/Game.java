package Domain.Game;

import Application.Controller;
import Domain.Board.Board;
import Domain.Player.Player;

public class Game implements InterfaceGame {

    private Player whitePlayer;
    private Player blackPlayer;
    private Board board;
    private Controller controller;
    private boolean isGameOver;

    public Game(Player whitePlayer, Player blackPlayer, Controller controller) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.controller = controller;
        this.board = new Board();
        this.isGameOver = false;
    }

    @Override
    public void startGame() {

    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public String getWinner() {
        return "";
    }
}
