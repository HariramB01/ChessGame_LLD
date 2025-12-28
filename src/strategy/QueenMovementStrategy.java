package strategy;

import utility.Board;
import utility.Cell;
import utility.Piece;
import utility.piece.Bishop;
import utility.piece.Rook;

public class QueenMovementStrategy implements MovementStrategy {
    @Override
    public boolean canMove(Board board, Cell from, Cell to, Piece piece) {
        return new RookMovementStrategy().canMove(board, from, to, piece)
                || new BishopMovementStrategy().canMove(board, from, to, piece);
    }
}
