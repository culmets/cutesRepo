package Domain;

import Application.Controller;

public class ComputerPlayer extends Player{

    public ComputerPlayer(String color, String name) {
        super(color, name);
    }

    @Override
    public void makeMove(Board board, Controller controller) {
        Move bestMove = calculateBestMove(board);
        board.movePiece(bestMove.getFrom(), bestMove.getTo());
    }


    private Move calculateBestMove(Board board) {
        // hier minimax implemntieren

        return null;
    }
}
