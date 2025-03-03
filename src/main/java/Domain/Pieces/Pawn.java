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
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        validMoves.addAll(computeForwardMoves(board));
        validMoves.addAll(computeDiagonalMoves(board));
        validMoves.removeIf(move -> !board.isKingSafeAfterMove(getPosition(), move, getColor()));
        return validMoves;
    }

    private List<Position> computeForwardMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int direction = getColor().equals("white") ? 1 : -1;
        int startRow = getColor().equals("white") ? 1 : 6;
        int currentRow = getPosition().row();
        int currentCol = getPosition().col();
        int forwardRow = currentRow + direction;

        if (Position.isWithinBounds(forwardRow, currentCol)) {
            Position forwardPosition = new Position(forwardRow, currentCol);
            if (board.getPieceAt(forwardPosition) == null) {
                moves.add(forwardPosition);
                if (currentRow == startRow) {
                    int doubleForwardRow = currentRow + 2 * direction;
                    if (Position.isWithinBounds(doubleForwardRow, currentCol)) {
                        Position doubleForwardPosition = new Position(doubleForwardRow, currentCol);
                        if (board.getPieceAt(doubleForwardPosition) == null) {
                            moves.add(doubleForwardPosition);
                        }
                    }
                }
            }
        }
        return moves;
    }

    private List<Position> computeDiagonalMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int direction = getColor().equals("white") ? 1 : -1;
        int currentCol = getPosition().col();
        int forwardRow = getPosition().row() + direction;
        int[] diagonalCols = {currentCol - 1, currentCol + 1};

        for (int diagCol : diagonalCols) {
            if (Position.isWithinBounds(forwardRow, diagCol)) {
                Position diagonalPosition = new Position(forwardRow, diagCol);
                AbstractChessPiece pieceAtDiagonal = board.getPieceAt(diagonalPosition);
                if (pieceAtDiagonal != null && !pieceAtDiagonal.getColor().equals(this.getColor())) {
                    moves.add(diagonalPosition);
                }
                else if (pieceAtDiagonal == null && board.getEnPassantTarget().isPresent() &&
                        board.getEnPassantTarget().get().equals(diagonalPosition)) {
                    moves.add(diagonalPosition);
                }
            }
        }
        return moves;
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

    public AbstractChessPiece promote() { // man könnte noch die Auswahl einer Figur die erstellt wird implementieren
        return new Queen(getColor(), getPosition());
    }
}
