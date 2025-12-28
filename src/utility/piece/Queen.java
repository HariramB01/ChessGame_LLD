package utility.piece;

import strategy.QueenMovementStrategy;
import utility.Board;
import utility.Cell;
import utility.Piece;

public class Queen extends Piece {

    public Queen(boolean isWhitePiece) {
        super(isWhitePiece, new QueenMovementStrategy());
    }

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return movementStrategy.canMove(board, startCell, endCell, this);
    }
}
