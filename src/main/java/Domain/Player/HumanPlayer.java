package Domain.Player;

import Application.Controller;
import Domain.Board.Board;
import Domain.Board.Position;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, String color) {
        super(name, color);
    }

    @Override
    public void makeMove(Board board, Controller controller) {
        Position start = controller.getMoveStart();
        Position end = controller.getMoveEnd();

        board.movePiece(start, end);
    }


}
