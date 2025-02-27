package Domain.Game;

import Application.Controller;
import Application.Command.GameCommand;
import Application.Command.MoveCommand;
import Application.Command.SaveCommand;
import Domain.Board.Board;
import Domain.Board.Position;
import Domain.Persistence.GameStateRepo;
import Domain.Persistence.LeaderboardRepository;
import Domain.Player.Player;
import Domain.Persistence.GameRecordRepo;
import Persistence.FileLeaderboardRepo;

import java.time.LocalDateTime;

public class Game implements InterfaceGame {

    private final Player whitePlayer;
    private final Player blackPlayer;
    private Board board;
    private final Controller controller;
    private boolean isGameOver;
    private String winner = "Unentschieden";
    private MoveHistory moveHistory;
    private final GameRecordRepo recordRepository;
    private final LeaderboardRepository leaderboardRepo;
    private final GameStateRepo gameStateRepo;

    private Player currentPlayer;
    private int moveCounter = 0;

    public Game(Player whitePlayer, Player blackPlayer, Controller controller, MoveHistory moveHistory, Board board, GameRecordRepo repository, FileLeaderboardRepo leaderboardRepo, GameStateRepo gameStateRepo) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.controller = controller;
        this.moveHistory = moveHistory;
        this.board = board;
        this.recordRepository = repository;
        this.leaderboardRepo = leaderboardRepo;
        this.gameStateRepo = gameStateRepo;
        this.isGameOver = false;
        this.currentPlayer = whitePlayer; //damit der weiße als default anfängt -> wenn gameState geladen wird, wird currenPlayer extra gesetzt.
    }

    @Override
    public void startGame() {
        controller.startGame();

        while (!isGameOver) {
            System.out.println("Zug für: " + currentPlayer.getName() + ", " + currentPlayer.getColor());
            Position start, end;
            boolean validMove = false;

            while (!validMove) { // wdh bis eingabe richtig -> ein Zug
                board.printBoard();
                GameCommand command = controller.getCommand();
                if (command instanceof SaveCommand) {
                    System.out.println("Aktuelles Spiel wird gespeichert");
                    GameState gameState = new GameState(currentPlayer.getColor(), board.toString(), moveHistory.toString(), currentPlayer.getName(), (currentPlayer == whitePlayer) ? blackPlayer.getName() : whitePlayer.getName());
                    System.out.println("Aktuelles Spiel gespeichert unter dem Namen: " + gameStateRepo.saveGameState(gameState));
                    System.exit(0);
                } else if (command instanceof MoveCommand) {

                    start = ((MoveCommand) command).start();
                    end = ((MoveCommand) command).end();

                    if (board.movePiece(start, end, currentPlayer.getColor())) {
                        //speichern des moves
                        moveCounter++;
                        Move move = new Move(start, end, moveCounter); // nochmal schaune mit movetype, braucht man das?
                        moveHistory.addMove(move);
                        validMove = true;
                    }
                }
            }
            currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
            isGameOver = isGameOver(currentPlayer); // hier ist schon der nächste wieder dran bzw als current player markiert
        }
        GameRecord record = new GameRecord(whitePlayer.getName(), blackPlayer.getName(), winner, moveHistory.getDemMoves(), LocalDateTime.now());
        recordRepository.saveGameRecord(record);
        leaderboardRepo.updateWin(winner);
        // gameSTate file löschen
        controller.endGame();
    }

    @Override
    public boolean isGameOver(Player currentPlayer) {
        System.out.println("Checking for checkmate");
        if (board.isCheckmate(currentPlayer.getColor())) {
            Player winner = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer; // wenn currentPlayer schachmatt dann hat der andere nicht currentPlayer gewonnen
            System.out.println("Schachmatt! " + winner.getName() + " hat gewonnen.");
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

    public void setCurrentTurn(String activePlayerColor) {
        if(activePlayerColor.equals("white")){
            currentPlayer = whitePlayer;
        } else {
            currentPlayer = blackPlayer;
        }
    }
}
