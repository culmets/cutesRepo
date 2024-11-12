package Domain.Player;

import Application.Controller;
import Domain.Board.Board;

public abstract class Player {

    protected String name;

    protected String color;

    public Player(String name, String color) {
        this.color = color;
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public abstract void makeMove(Board board, Controller controller);
}
