package strategy;

import utility.Board;
import utility.Cell;
import utility.Piece;

public class BishopMovementStrategy implements MovementStrategy{
    @Override
    public boolean canMove(Board board, Cell from, Cell to, Piece piece) {
//        (0,2) -> (5,7): (1,3), (2,4), (3,5), (4,6)
//        (5,7) -> (0,2): (4,6), (3,5), (2,4), (1,3)
        int dr = Math.abs(from.getRow() - to.getRow()); // (0-5) -> 5
        int dc = Math.abs(from.getCol() - to.getCol()); // (2-7) -> 5
        if (dr != dc) return false;
        int rs = Integer.compare(to.getRow(), from.getRow()); // (5,0) -> 1
        int cs = Integer.compare(to.getCol(), from.getCol()); // (7,2) -> 1
        int r = from.getRow() + rs, c = from.getCol() + cs; // r -> (0+1): 1, c -> (2+1): 3
        while (r != to.getRow()) { // 1!=5
            if (!board.getCell(r, c).isEmpty()) return false;
            r += rs; c += cs;
        }
        return true;
    }
}

/**
 *
 * Integer.compare(a, b) returns:
 *
 * 1 if a > b
 *
 * 0 if a == b
 *
 * -1 if a < b
 *
 */