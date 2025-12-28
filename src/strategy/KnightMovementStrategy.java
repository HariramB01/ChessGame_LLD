package strategy;

import utility.Board;
import utility.Cell;
import utility.Piece;

public class KnightMovementStrategy implements MovementStrategy {
    @Override
    public boolean canMove(Board board, Cell from, Cell to, Piece piece) {
        int dr = Math.abs(from.getRow() - to.getRow());
        int dc = Math.abs(from.getCol() - to.getCol());
        return dr * dc == 2;
    }
}

/**
 *
 * A knight moves in an L-shape:
 * 2 squares in one direction
 * 1 square perpendicular
 * It can jump over pieces
 *
 * So valid moves are:
 *
 * (2 rows, 1 column)
 *
 * (1 row, 2 columns)
 *
 * From (4,4):
 *
 * (6,5) → dr=2, dc=1
 *
 * (6,3) → dr=2, dc=1
 *
 * (5,6) → dr=1, dc=2
 *
 * (3,2) → dr=1, dc=2
 *
 * All pass dr * dc == 2
 *
 */