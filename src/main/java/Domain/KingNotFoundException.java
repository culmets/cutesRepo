package Domain;

public class KingNotFoundException extends RuntimeException {

    public KingNotFoundException(String color) {
        super("King of color " + color + " not found on the board. Game terminated");
    }

}

