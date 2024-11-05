package Domain;

import Application.Controller;

public abstract class Player {

    protected String name;

    protected String color;

    public Player(String color, String name) {
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
