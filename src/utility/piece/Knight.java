package utility.piece;

import strategy.KnightMovementStrategy;
import utility.Board;
import utility.Cell;
import utility.Piece;

public class Knight extends Piece {
    public Knight(boolean isWhitePiece) {
        super(isWhitePiece, new KnightMovementStrategy());
    }

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return movementStrategy.canMove(board, startCell, endCell, this);
    }
}
