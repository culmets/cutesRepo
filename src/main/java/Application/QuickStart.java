package Application;

import Domain.Board.Board;
import Domain.Game.Game;
import Domain.Game.MoveHistory;
import Domain.Persistence.GameRecordRepo;
import Domain.Persistence.GameStateRepo;
import Domain.Player.HumanPlayer;
import Domain.Player.Player;
import Persistence.FileGameRecordRepo;
import Persistence.FileGameStateRepo;
import Persistence.FileLeaderboardRepo;

import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class QuickStart {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        String basePathGameRecord = System.getProperty("user.dir") + File.separator + "saved_games";
        String basePathLeaderboard = System.getProperty("user.dir") + File.separator + "leaderboard";
        String basePathGameState = System.getProperty("user.dir") + File.separator + "game_state_";

        Player whitePlayer = new HumanPlayer("A", "white");
        Player blackPlayer = new HumanPlayer("B", "black");

        GameRecordRepo repository = new FileGameRecordRepo(basePathGameRecord);
        FileLeaderboardRepo leaderboardRepo = new FileLeaderboardRepo(basePathLeaderboard);
        GameStateRepo gameStateRepo = new FileGameStateRepo(basePathGameState);
        Controller controller = new CLIController();
        MoveHistory moveHistory = new MoveHistory();
        Board board = new Board();

        Game game = new Game(whitePlayer, blackPlayer, controller, moveHistory, board, repository, leaderboardRepo, gameStateRepo);

        game.startGame();
    }
}
