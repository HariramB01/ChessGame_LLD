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
