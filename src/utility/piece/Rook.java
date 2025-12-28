package utility.piece;

import strategy.RookMovementStrategy;
import utility.Board;
import utility.Cell;
import utility.Piece;

public class Rook extends Piece {

    private boolean hasMoved = false;

    public Rook(boolean isWhitePiece) {
        super(isWhitePiece, new RookMovementStrategy());
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return movementStrategy.canMove(board, startCell, endCell, this);
    }
}
