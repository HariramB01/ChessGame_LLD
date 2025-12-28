package strategy;

import utility.Board;
import utility.Cell;
import utility.Piece;

public class RookMovementStrategy implements MovementStrategy{
    @Override
    public boolean canMove(Board board, Cell from, Cell to, Piece piece) {
// (0,0) (7,0) moving in same col
// (2,0) (2,7) moving in same row
// at least row/col should have same value, else its incorrect
        if (from.getRow() != to.getRow() && from.getCol() != to.getCol()) return false;
//        (6,2) -> (2,2): movement: (5,2), (4,2), (3,2)
        int rs = Integer.compare(to.getRow(), from.getRow()); // -1
        int cs = Integer.compare(to.getCol(), from.getCol()); // 0
        int r = from.getRow() + rs, c = from.getCol() + cs; // 6+(-1), 2+0 -> rs = 5, cs = 2
        while (r != to.getRow() || c != to.getCol()) { // while(5!=2 || 2!=2)
            // checking every row, if any piece in the middle/interrupts return false
            if (!board.getCell(r, c).isEmpty()) return false;
            r += rs; c += cs;
        }
        return true;
    }
}


/**
 *
 * note:
 *
 * move straight (vertical/horizontal)
 * vertical -> same col value
 * horizontal -> same row value
 *
 * base Case:
 * 1. if col/row doesn't have a same value return false
 * 2. if there's any piece in the middle return false
 * 3. if the destination cell has opposite color piece return false
 *
 * else return true
 *
 */