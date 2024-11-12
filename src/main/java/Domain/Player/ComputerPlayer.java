package Domain.Player;

import Application.Controller;
import Domain.Board.Board;
import Domain.Board.Move;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String color, String name) {
        super(color, name);
    }

    @Override
    public void makeMove(Board board, Controller controller) {
        Move bestMove = calculateBestMove(board);
        board.movePiece(bestMove.getFrom(), bestMove.getTo(), color);
    }


    private Move calculateBestMove(Board board) {
        // hier minimax implemntieren
        //figuren mit computer farbe suchen

        return null;
    }
}
