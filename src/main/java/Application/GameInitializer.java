package Application;

import Domain.Board.Board;
import Domain.Game.Game;
import Domain.Game.GameState;
import Domain.Game.MoveHistory;
import Domain.Persistence.GameRecordRepo;
import Domain.Persistence.GameStateRepo;
import Domain.Persistence.LeaderboardRepository;
import Domain.Player.HumanPlayer;
import Domain.Player.Player;
import Persistence.FileGameRecordRepo;
import Persistence.FileGameStateRepo;
import Persistence.FileLeaderboardRepo;

import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

import static java.lang.System.exit;

public class GameInitializer {

    // Abhängigkeiten nur als Schnittstellen, nicht als konkrete Klassen
    private final LeaderboardRepository leaderboardRepo;
    private final GameStateRepo gameStateRepo;
    private final GameRecordRepo gameRecordRepo;

    // Konstruktor erhält die Abhängigkeiten als Schnittstellen (Dependency Injection)
    public GameInitializer(LeaderboardRepository leaderboardRepo, GameStateRepo gameStateRepo, GameRecordRepo gameRecordRepo) {
        this.leaderboardRepo = leaderboardRepo;
        this.gameStateRepo = gameStateRepo;
        this.gameRecordRepo = gameRecordRepo;
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        String basePathGameRecord = System.getProperty("user.dir") + File.separator + "saved_games";
        String basePathLeaderboard = System.getProperty("user.dir") + File.separator + "leaderboard";
        String basePathGameState = System.getProperty("user.dir") + File.separator + "game_state_";

        // Instanziierung der konkreten Implementierungen – hier nur einmal zur Laufzeit
        LeaderboardRepository leaderboardRepo = new FileLeaderboardRepo(basePathLeaderboard);
        GameStateRepo gameStateRepo = new FileGameStateRepo(basePathGameState);
        GameRecordRepo gameRecordRepo = new FileGameRecordRepo(basePathGameRecord);

        // Übergabe der Abhängigkeiten als Schnittstellen an den GameInitializer
        GameInitializer initializer = new GameInitializer(leaderboardRepo, gameStateRepo, gameRecordRepo);
        initializer.initializeGame();
    }

    // Auslagerung der Initialisierungslogik in eine Instanzmethode
    private void initializeGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Schach");
        System.out.println("Wählen Sie den Spielmodus:");
        System.out.println("1 - Zwei menschliche Spieler");
        System.out.println("2 - Spielstand laden");
        System.out.println("3 - Leaderboard anzeigen");

        int choice;
        while (true) {
            System.out.print("Eingabe 1,2 oder 3: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) {
                    break;
                } else {
                    System.out.println("Ungültige Eingabe. Bitte geben Sie 1, 2 oder 3 ein.");
                }
            } else {
                System.out.println("Ungültige Eingabe. Bitte geben Sie 1, 2 oder 3 ein.");
                scanner.next();
            }
        }

        if(choice == 3) {
            System.out.println("Leaderboard wird ausgegeben:");
            leaderboardRepo.printLeaderboard();
            exit(0);
        } else if (choice == 2) {
            GameStateWrapper wrapper = chooseGameState(gameStateRepo);
            Optional<GameState> savedGame = wrapper.gameState();

            if(savedGame.isPresent()){
                GameState loadedState = savedGame.get();
                Board board = Board.fromString(loadedState.boardState());
                MoveHistory history = MoveHistory.fromString(loadedState.moveHistory());
                String activePlayerColor = loadedState.activePlayer();
                String activePlayerName = loadedState.activePlayerName();
                String otherPlayerName = loadedState.otherPlayerName();

                Player whitePlayer, blackPlayer;
                if(activePlayerColor.equalsIgnoreCase("white")){
                    whitePlayer = new HumanPlayer(activePlayerName, "white");
                    blackPlayer = new HumanPlayer(otherPlayerName, "black");
                } else {
                    whitePlayer = new HumanPlayer(otherPlayerName, "white");
                    blackPlayer = new HumanPlayer(activePlayerName, "black");
                }

                Game game = new Game(whitePlayer, blackPlayer, new CLIController(), history, board, gameRecordRepo, leaderboardRepo, gameStateRepo);
                game.setCurrentTurn(activePlayerColor);
                game.setFileName(wrapper.fileName());
                game.startGame();
                exit(0);
            }
            System.out.println("Es wurde kein Spielstand gefunden :(");
            exit(0);
        }

        // Normales Spiel
        System.out.print("Name des weißen Spielers: ");
        String whiteName = scanner.next();
        Player whitePlayer = new HumanPlayer(whiteName, "white");

        System.out.print("Name des schwarzen Spielers: ");
        String blackName = scanner.next();
        Player blackPlayer = new HumanPlayer(blackName, "black");

        Game game = new Game(whitePlayer, blackPlayer, new CLIController(), new MoveHistory(), new Board(), gameRecordRepo, leaderboardRepo, gameStateRepo);
        game.startGame();
    }

    private static GameStateWrapper chooseGameState(GameStateRepo gameStateRepo) {
        List<String> fileNames = gameStateRepo.listGameStateFileNames();
        System.out.println("Bitte wähle aus den folgenden Dateien aus, welcher Spielstand weitergespielt werden soll:");
        IntStream.range(0, fileNames.size())
                .forEach(i -> System.out.println(i + ": " + fileNames.get(i)));

        int choice;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Geben Sie die Nummer des zu ladenden Spielstandes ein: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < fileNames.size()) {
                    break;
                } else {
                    System.out.println("Ungültige Eingabe.");
                }
            } else {
                System.out.println("Ungültige Eingabe.");
                scanner.next();
            }
        }
        return new GameStateWrapper(fileNames.get(choice), gameStateRepo.loadGameState(fileNames.get(choice)));
    }
}
