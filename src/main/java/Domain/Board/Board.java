package Domain.Board;

import Domain.Exceptions.KingNotFoundException;
import Domain.Pieces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private Map<Position, AbstractChessPiece> board;

    public Board() {
        this.board = new HashMap<>();
        initializePieces();
    }

    public Board(boolean empty) {
        this.board = new HashMap<>();
        if (!empty) {
            initializePieces();
        }
    }

    private void initializePieces() {
        board.put(new Position(0, 0), new Rook("white", new Position(0, 0)));
        board.put(new Position(0, 1), new Knight("white", new Position(0, 1)));
        board.put(new Position(0, 2), new Bishop("white", new Position(0, 2)));
        board.put(new Position(0, 3), new Queen("white", new Position(0, 3)));
        board.put(new Position(0, 4), new King("white", new Position(0, 4)));
        board.put(new Position(0, 5), new Bishop("white", new Position(0, 5)));
        board.put(new Position(0, 6), new Knight("white", new Position(0, 6)));
        board.put(new Position(0, 7), new Rook("white", new Position(0, 7)));

        for (int col = 0; col < 8; col++) {
            board.put(new Position(1, col), new Pawn("white", new Position(1, col)));
        }

        board.put(new Position(7, 0), new Rook("black", new Position(7, 0)));
        board.put(new Position(7, 1), new Knight("black", new Position(7, 1)));
        board.put(new Position(7, 2), new Bishop("black", new Position(7, 2)));
        board.put(new Position(7, 3), new Queen("black", new Position(7, 3)));
        board.put(new Position(7, 4), new King("black", new Position(7, 4)));
        board.put(new Position(7, 5), new Bishop("black", new Position(7, 5)));
        board.put(new Position(7, 6), new Knight("black", new Position(7, 6)));
        board.put(new Position(7, 7), new Rook("black", new Position(7, 7)));

        for (int col = 0; col < 8; col++) {
            board.put(new Position(6, col), new Pawn("black", new Position(6, col)));
        }
    }


    public AbstractChessPiece getPieceAt(Position position) {
        return board.get(position);
    }

    // hier noch schleife oder try catch machen?
    public boolean movePiece(Position start, Position end, String color) {
        AbstractChessPiece piece = board.get(start);

        if (piece == null) {
            System.out.println("No piece at the start position.");
            return false;
        }

        if (!isValidMove(start, end, color)) {
            System.out.println("Invalid move for " + piece.getClass().getSimpleName());
            return false;
        }

        board.put(end, piece);
        board.remove(start);
        piece.setPosition(end);
        return true;
    }

    public void printBoard() {
        System.out.println("    A  B  C  D  E  F  G  H");
        System.out.println(" +------------------------+");

        for (int row = 7; row >= 0; row--) {
            System.out.print((row + 1) + "| ");

            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                AbstractChessPiece piece = board.get(pos);
                if (piece == null) {
                    System.out.print(" . ");
                } else {
                    System.out.print(piece.getSymbol() + " ");
                }
            }
            System.out.println("| " + (row + 1));
        }
        System.out.println(" +------------------------+");
        System.out.println("    A  B  C  D  E  F  G  H");
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();
    }


    public boolean isWithinBoard(Position newPos) {
        return newPos.row() >= 0 && newPos.row() <= 7 && newPos.col() >= 0 && newPos.col() <= 7;
    }


    //prüfen ob der König einer Farbe im Schach steht indem alle Pieces der anderen Farbe gecheckt werden
  /*  public boolean isKingInCheck(String color) {
        Position kingPosition = findKingPosition(color);
        for (AbstractChessPiece piece : board.values()) {
            if (!piece.getColor().equals(color) && piece.getValidMoves(this).contains(kingPosition)) {
                return true;
            }
        }
        return false;
    }
*/
    public boolean isKingInCheck(String color) {
        Position kingPosition = findKingPosition(color);
        return isPositionUnderThreat(kingPosition, color);
    }


    public boolean isKingSafeAfterMove(Position start, Position end, String color) {
        AbstractChessPiece piece = getPieceAt(start);
        AbstractChessPiece capturedPiece = getPieceAt(end);
        // testet den Zug
        board.put(end, piece);
        board.remove(start);
        piece.setPosition(end);

        Position kingPosition = piece instanceof King ? end : findKingPosition(color);
        boolean kingIsSafe = !isPositionUnderThreat(kingPosition, color);
     //   boolean kingIsSafe = !isKingInCheck(color);

        // macht den Zug rückgängig
        board.put(start, piece);
        piece.setPosition(start);
        if (capturedPiece != null) {
            board.put(end, capturedPiece);
        } else {
            board.remove(end);
        }
        return kingIsSafe;
    }

    private boolean isPositionUnderThreat(Position position, String color) {
            for (AbstractChessPiece piece : getPiecesByColor((color.equals("white")) ? "black" : "white")) { // nur gegnerische farbe
                if (piece.canAttack(position, this)) {
                    return true;
                }
            }
            return false;
    }


    // sucht die Figur in der Map, die ein König ist und die richtige Farbe hat
    // wird durch testen von anderen methoden mitgetestet -> kapselung bleibt erhalten
    private Position findKingPosition(String color) {
        for (Map.Entry<Position, AbstractChessPiece> entry : board.entrySet()) {
            if (entry.getValue() instanceof King && entry.getValue().getColor().equals(color)) {
                return entry.getKey();
            }
        }
        throw new KingNotFoundException(color);
    }


    // ob zwischen start+1 und end-1 eine figur steht
    public boolean isPathClear(Position start, Position end) {
        // in welcher Richtung ist die Änderung -> .compare() gibt 1, 0, -1 zurück
        int rowDiff = Integer.compare(end.row(), start.row()); // (2,4)
        int colDiff = Integer.compare(end.col(), start.col()); // (4,5)
        // ersten Schritt in richtige Richtung(en)
        int currentRow = start.row() + rowDiff;
        int currentCol = start.col() + colDiff;

        while (currentRow != end.row() || currentCol != end.col()) {
            Position currentPosition = new Position(currentRow, currentCol);
            if (getPieceAt(currentPosition) != null) {
                return false; // wenn was im Weg
            }
            currentRow += rowDiff;
            currentCol += colDiff;
        }
        return true; // wenn nichts im Weg
    }

    public boolean isCheckmate(String color) {
        if (!isKingInCheck(color)) {
            return false;
        }
        return !hasLegalMoves(color);
    }

    public boolean isStalemate(String color) {
        if (isKingInCheck(color)) {
            return false;
        }
        return !hasLegalMoves(color);
    }

    //doppelprüfung auf isKingSafe
    private boolean hasLegalMoves(String color) {
        for (AbstractChessPiece piece : getPiecesByColor(color)) {
            List<Position> validMoves = piece.getValidMoves(this);
            for (Position move : validMoves) {
                if (isKingSafeAfterMove(piece.getPosition(), move, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<AbstractChessPiece> getPiecesByColor(String color) {
        List<AbstractChessPiece> pieces = new ArrayList<>();
        for (AbstractChessPiece piece : board.values()) {
            if (piece.getColor().equals(color)) {
                pieces.add(piece);
            }
        }
        return pieces;
    }

    public boolean isValidMove(Position start, Position end, String color) {
        AbstractChessPiece piece = getPieceAt(start);
        if (piece == null || !piece.getColor().equals(color)) {
            System.out.println("Figur des Gegners ausgewählt.");
            return false; // keine Figur auf startpos oder andersfarbige figur
        }
        if (!isWithinBoard(end)) {
            System.out.println("Zielposition ist nicht auf dem Brett");
            return false;
        }
        List<Position> validMoves = piece.getValidMoves(this);
        if (!validMoves.contains(end)) {
            System.out.println("Zug ist ungültig. (König im Schach o. Ä.)");
            return false; // Zug ungültig
        }
        return isKingSafeAfterMove(start, end, color); // letzte prüfung, ob könig im schach steht danach
    }

    public void placePiece(AbstractChessPiece piece, Position position) {
        if (!isWithinBoard(position)) {
            throw new IllegalArgumentException("Die Position " + position + " liegt außerhalb des Bretts.");
        }
        board.put(position, piece);
        piece.setPosition(position);
    }


}
