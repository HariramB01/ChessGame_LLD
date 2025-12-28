package strategy;

import utility.Board;
import utility.Cell;
import utility.Piece;

public class PawnMovementStrategy implements MovementStrategy {
    @Override
    public boolean canMove(Board board, Cell from, Cell to, Piece piece) {
        // based on the piece color white -> row+1, black -> row -1
        int dir = piece.isWhitePiece() ? 1 : -1;
        // based on the piece color pawn start column white -> 1, black -> 6
        int start = piece.isWhitePiece() ? 1 : 6;

        // column should be equal
        if (from.getCol() == to.getCol()) {
            // (1,0) -> (2,0) -> (2-1) -> 1 && to cell should be empty
            if (to.getRow() - from.getRow() == dir && board.getCell(to.getRow(), to.getCol()).isEmpty())
                return true;
            /**
             * (1,4) → (3,4)
             *Pawn is on its starting row
             * Moves exactly 2 squares forward
             * Target square is empty
             * Square in between is empty
             *
             * (1,4) → (3,4)
             * Square (2,4) is occupied
             * blocked by board.getCell(from.getRow() + dir, from.getCol()).isEmpty()
             *
             *
             * Pawn already moved to row 2
             * (2,4) → (4,4)
             *
             * from.getRow() == start
             */
            if (from.getRow() == start && to.getRow() - from.getRow() == 2 * dir &&
                    board.getCell(to.getRow(), to.getCol()).isEmpty() &&
                    board.getCell(from.getRow() + dir, from.getCol()).isEmpty())
                return true;
        }
        /**
         *
         * White pawn at (1,4)
         * Enemy piece at (2,5)
         * Move → (2,5)
         * square should not be empty
         *
         */
        if (Math.abs(from.getCol() - to.getCol()) == 1 &&
                to.getRow() - from.getRow() == dir &&
                !board.getCell(to.getRow(), to.getCol()).isEmpty())
            return true;
        return false;
    }
}
