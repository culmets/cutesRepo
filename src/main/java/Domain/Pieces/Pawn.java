package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends AbstractChessPiece implements ChessPiece {
    public Pawn(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isMoveValid(Position moveFRom, Position moveTo, Board board) {
        return false;
    }

    //en passant noch implementieren
    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        int direction = getColor().equals("white") ? 1 : -1;  //weiße bauern ziehen hoch und schwarze runter
        int startRow = getColor().equals("white") ? 1 : 6;
        int currentRow = getPosition().row();
        int currentCol = getPosition().col();
        int forwardRow = currentRow + direction;

        if (Position.isWithinBounds(forwardRow, currentCol)) {
            Position forwardPosition = new Position(forwardRow, currentCol);
            if (board.getPieceAt(forwardPosition) == null) { // feld ist frei
                validMoves.add(forwardPosition);

                // zwei felder im ersten zug
                if (currentRow == startRow) {
                    int doubleForwardRow = currentRow + 2 * direction;
                    if (Position.isWithinBounds(doubleForwardRow, currentCol)) {
                        Position doubleForwardPosition = new Position(doubleForwardRow, currentCol);
                        if (board.getPieceAt(doubleForwardPosition) == null) {
                            validMoves.add(doubleForwardPosition);
                        }
                    }
                }
            }
        }
        // diagonal schlagen
        int[] diagonalCols = {currentCol - 1, currentCol + 1}; // Links und rechts

        for (int diagCol : diagonalCols) {
            if (Position.isWithinBounds(forwardRow, diagCol)) {
                Position diagonalPosition = new Position(forwardRow, diagCol);
                AbstractChessPiece pieceAtDiagonal = board.getPieceAt(diagonalPosition);
                // nur gegner schlagen
                if (pieceAtDiagonal != null && !pieceAtDiagonal.getColor().equals(this.getColor())) {
                    validMoves.add(diagonalPosition);
                }
            }
        }
        validMoves.removeIf(move -> !board.isKingSafeAfterMove(this.getPosition(), move, this.getColor()));
        return validMoves;
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " P" : " p";
    }

    @Override
    public boolean canAttack(Position targetPosition, Board board) {
        int direction = getColor().equals("white") ? 1 : -1;
        int targetRow = getPosition().row() + direction;

        return (targetPosition.row() == targetRow) &&
                (targetPosition.col() == getPosition().col() - 1 || targetPosition.col() == getPosition().col() + 1);
    }
}
