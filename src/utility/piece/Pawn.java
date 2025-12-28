package utility.piece;

import strategy.PawnMovementStrategy;
import utility.Board;
import utility.Cell;
import utility.Piece;

public class Pawn extends Piece {

    public Pawn(boolean isWhitePiece) {
        super(isWhitePiece, new PawnMovementStrategy());
    }

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return movementStrategy.canMove(board, startCell, endCell, this);
    }

}
