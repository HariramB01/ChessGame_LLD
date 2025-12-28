package strategy;

import utility.Board;
import utility.Cell;
import utility.Piece;

public interface MovementStrategy {
    boolean canMove(Board board, Cell startCell, Cell endCell, Piece piece);
}
