package Domain.Pieces;

import Domain.Board.Board;
import Domain.Board.Position;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractChessPiece implements ChessPiece {
    public Rook(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return (getColor().equals("white")) ? " R" : " r";
    }

    @Override
    public boolean isMoveValid(Position moveFrom, Position moveTo, Board board) {
        return getValidMoves(board).contains(moveTo);
    }

    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        for (int[] direction : directions) {
            int row = getPosition().row();
            int col = getPosition().col();
            // Gehe in der aktuellen Richtung, bis wir außerhalb des Boards sind oder auf ein Hindernis stoßen
            while (true) {
                row += direction[0];
                col += direction[1];
                Position newPosition = new Position(row, col);
                if (!board.isWithinBoard(newPosition)) {
                    break;
                }
                // wenn piece auf neuer pos
                AbstractChessPiece pieceAtNewPosition = board.getPieceAt(newPosition);
                if (pieceAtNewPosition != null) {
                    //
                    if (!pieceAtNewPosition.getColor().equals(this.getColor())) {
                        validMoves.add(newPosition); // wenn gegner draufsteht, kann man schlagen
                    }
                    break; //pos ist beleg und richtung damit fertig geprüft
                }
                validMoves.add(newPosition); //pos war frei
            }
        }

        return validMoves;
    }
}
