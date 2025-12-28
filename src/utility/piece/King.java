package utility.piece;

import strategy.KingMovementStrategy;
import utility.Board;
import utility.Cell;
import utility.Piece;

public class King extends Piece {

    private boolean moved = false;

    public King(boolean isWhitePiece) {
        super(isWhitePiece, new KingMovementStrategy());
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved() {
        moved = true;
    }

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return movementStrategy.canMove(board, startCell, endCell, this);
    }
}
