package strategy;

import utility.Board;
import utility.Cell;
import utility.Piece;
import utility.piece.King;
import utility.piece.Rook;

public class KingMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMove(Board board, Cell from, Cell to, Piece piece) {

        King king = (King) piece;

        /* =====================================================
           1️⃣ BASIC VALIDATIONS
           ===================================================== */

        // Destination must be inside board
        if (!board.isInsideBoard(to)) return false;

        // Cannot capture own piece
        if (!to.isEmpty()
                && to.getPiece().isWhitePiece() == king.isWhitePiece()) {
            return false;
        }

        int dr = Math.abs(from.getRow() - to.getRow());
        int dc = Math.abs(from.getCol() - to.getCol());

        /* =====================================================
           2️⃣ NORMAL KING MOVE (ONE SQUARE ANY DIRECTION)
           ===================================================== */

        if (dr <= 1 && dc <= 1) {

            // Cannot remain on same square
            if (dr == 0 && dc == 0) return false;

            // King cannot move into an attacked square
            return !board.isSquareUnderAttack(to, king.isWhitePiece());
        }

        /* =====================================================
           3️⃣ CASTLING VALIDATION (NO BOARD MUTATION)
           ===================================================== */

        if (!king.hasMoved() && dr == 0 && dc == 2) {
            return canCastle(board, from, to, king);
        }

        return false;
    }

    /**
     * Validates whether castling is legally allowed.
     * This method only checks rules — it does NOT move any pieces.
     */
    private boolean canCastle(Board board, Cell from, Cell to, King king) {

        int direction = (to.getCol() > from.getCol()) ? 1 : -1;
        int rookCol = (direction == 1) ? 7 : 0;

        // Locate rook cell
        Cell rookCell = board.getCell(from.getRow(), rookCol);
        if (rookCell == null || rookCell.isEmpty()) return false;

        Piece rookPiece = rookCell.getPiece();

        // Rook must be same color and must not have moved
        if (!(rookPiece instanceof Rook)) return false;
        if (rookPiece.isWhitePiece() != king.isWhitePiece()) return false;
        if (((Rook) rookPiece).isHasMoved()) return false;

        /* =====================================================
           Path Validation: No pieces between king and rook
           ===================================================== */

        for (int c = from.getCol() + direction; c != rookCol; c += direction) {
            if (!board.getCell(from.getRow(), c).isEmpty()) {
                return false;
            }
        }

        /* =====================================================
           Safety Validation:
           King cannot be:
           - currently in check
           - pass through check
           - end in check
           ===================================================== */

        for (int c = from.getCol(); c != to.getCol() + direction; c += direction) {
            Cell square = board.getCell(from.getRow(), c);
            if (board.isSquareUnderAttack(square, king.isWhitePiece())) {
                return false;
            }
        }

        return true;
    }
}
