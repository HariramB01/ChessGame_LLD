package strategy;

import utility.Board;
import utility.Cell;
import utility.Piece;

public class PawnMovementStrategy implements MovementStrategy {
    @Override
    public boolean canMove(Board board, Cell from, Cell to, Piece piece) {
        int dir = piece.isWhitePiece() ? 1 : -1;
        int start = piece.isWhitePiece() ? 1 : 6;

        if (from.getCol() == to.getCol()) {
            if (to.getRow() - from.getRow() == dir && board.getCell(to.getRow(), to.getCol()).isEmpty())
                return true;
            if (from.getRow() == start && to.getRow() - from.getRow() == 2 * dir &&
                    board.getCell(to.getRow(), to.getCol()).isEmpty() &&
                    board.getCell(from.getRow() + dir, from.getCol()).isEmpty())
                return true;
        }
        if (Math.abs(from.getCol() - to.getCol()) == 1 &&
                to.getRow() - from.getRow() == dir &&
                !board.getCell(to.getRow(), to.getCol()).isEmpty())
            return true;
        return false;
    }
}
