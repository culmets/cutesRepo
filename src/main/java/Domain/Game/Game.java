package Domain.Game;

import Application.Controller;
import Domain.Board.Board;
import Domain.Board.Position;
import Domain.Player.ComputerPlayer;
import Domain.Player.HumanPlayer;
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
        controller.startGame();
        Player currentPlayer = whitePlayer;

        while (!isGameOver) {
            System.out.println("Zug für: " + currentPlayer.getName());
            Position start, end;
            boolean validMove = false;

            // wdh bis eingabe richtig
            while (!validMove) {
                start = controller.getMoveStart();
                end = controller.getMoveEnd();

                if (board.isValidMove(start, end, currentPlayer.getColor())) {
                    board.movePiece(start, end);
                    validMove = true;
                } else {
                    System.out.println("Ungültiger Zug. Bitte erneut versuchen.");
                }
            }
            isGameOver = isGameOver();
            currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
        }
        controller.endGame();
        System.out.println("Gewinner: " + getWinner());
    }

    @Override
    public boolean isGameOver() {
        if (board.isCheckmate(whitePlayer.getColor())) {
            System.out.println("Schachmatt! " + blackPlayer.getName() + " hat gewonnen.");
            return true;
        }
        if (board.isCheckmate(blackPlayer.getColor())) {
            System.out.println("Schachmatt! " + whitePlayer.getName() + " hat gewonnen.");
            return true;
        }
        if (board.isStalemate(whitePlayer.getColor()) || board.isStalemate(blackPlayer.getColor())) {
            System.out.println("Patt! Das Spiel endet unentschieden.");
            return true;
        }
        return false;
    }

    @Override
    public String getWinner() {
        if (board.isCheckmate(whitePlayer.getColor())) {
            return blackPlayer.getName();
        }
        if (board.isCheckmate(blackPlayer.getColor())) {
            return whitePlayer.getName();
        }
        return "Unentschieden";
    }
}
