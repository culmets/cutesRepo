package Domain.Game;

import Application.Controller;
import Domain.Board.Board;
import Domain.Board.Position;
import Domain.Player.Player;
import Persistence.FileGameRecordRepo;
import Domain.Persistence.GameRecordRepo;

import java.time.LocalDateTime;

public class Game implements InterfaceGame {

    private final Player whitePlayer;
    private final Player blackPlayer;
    private Board board;
    private final Controller controller;
    private boolean isGameOver;
    private String winner = "Unentschieden";
    private final MoveHistory moveHistory;

    private int moveCounter = 0;

    public Game(Player whitePlayer, Player blackPlayer, Controller controller, MoveHistory moveHistory) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.controller = controller;
        this.moveHistory = moveHistory;
        this.board = new Board();
        this.isGameOver = false;
    }

    @Override
    public void startGame() {
        controller.startGame();
        Player currentPlayer = whitePlayer;

        while (!isGameOver) {
            System.out.println("Zug für: " + currentPlayer.getName() + ", " + currentPlayer.getColor());
            Position start, end;
            boolean validMove = false;

            // wdh bis eingabe richtig
            while (!validMove) {
                board.printBoard();
                start = controller.getMoveStart();
                end = controller.getMoveEnd();

                if (board.movePiece(start, end, currentPlayer.getColor())) {
                    //speichern des moves
                    moveCounter++;
                    Move move = new Move(start, end, moveCounter, MoveType.NORMAL); // nochmal schaune mit movetype, braucht man das?
                    moveHistory.addMove(move);

                    validMove = true;
                }
            }
            currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
            isGameOver = isGameOver(currentPlayer); // hier ist schon der nächste wieder dran bzw als current player markiert
        }
        GameRecord record = new GameRecord(whitePlayer.getName(), blackPlayer.getName(), winner, moveHistory.getDemMoves(), LocalDateTime.now());
        GameRecordRepo repository = new FileGameRecordRepo();
        repository.saveGameRecord(record);
        controller.endGame();
    }

    @Override
    public boolean isGameOver(Player currentPlayer) {
        System.out.println("Checking for checkmate");
        if (board.isCheckmate(currentPlayer.getColor())) {
            Player winner = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer; // wenn currentPlayer schachmatt dann hat der andere nicht currentPlayer gewonnen
            System.out.println("Schachmatt! " + winner + " hat gewonnen.");
            setWinner(winner.getName());
            return true;
        }

        if (board.isStalemate(currentPlayer.getColor())) {
            System.out.println("Patt! Das Spiel endet unentschieden.");
            return true;
        }
        return false;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public String getWinner() {
        return winner;
    }
}
