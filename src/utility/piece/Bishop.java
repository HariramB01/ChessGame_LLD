package utility.piece;

import strategy.BishopMovementStrategy;
import utility.Board;
import utility.Cell;
import utility.Piece;

public class Bishop extends Piece {
    public Bishop(boolean isWhitePiece) {
        super(isWhitePiece, new BishopMovementStrategy());
    }

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return movementStrategy.canMove(board, startCell, endCell, this);
    }
}
