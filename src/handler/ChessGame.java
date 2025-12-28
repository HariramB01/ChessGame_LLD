package handler;

import utility.*;
import utility.piece.King;
import utility.piece.Rook;

import java.util.ArrayList;

public class ChessGame {

    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    public boolean isWhiteTurn = true;
    private ArrayList<Move> gameLog = new ArrayList<>();

    public ChessGame(Board board, Player white, Player black) {
        this.board = board;
        this.whitePlayer = white;
        this.blackPlayer = black;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public Board getBoard() {
        return board;
    }

    public boolean makeMove(Player player, Cell from, Cell to) {

    /* =====================================================
       1️⃣ BASIC VALIDATIONS (NO BOARD STATE MUTATION)
       ===================================================== */

        // Rule: A move must start from a non-empty cell
        if (from.isEmpty()) {
            System.out.println("No piece at source!");
            return false;
        }

        // Fetch the piece being moved
        Piece piece = from.getPiece();

        // Rule: Only the current player can move their own pieces
        // (Turn validation is enforced at the game level)
        if (piece.isWhitePiece() != player.isWhite()) {
            System.out.println("Not your turn!");
            return false;
        }

        // Rule: Piece-specific movement legality
        // (Handled by Strategy pattern for each piece type)
        if (!piece.canMove(board, from, to)) {
            System.out.println("Illegal move!");
            return false;
        }

        // Rule: Cannot capture your own piece
        if (!to.isEmpty() && to.getPiece().isWhitePiece() == player.isWhite()) {
            System.out.println("Cannot capture your own piece!");
            return false;
        }

    /* =====================================================
       2️⃣ SIMULATION PHASE (TEMPORARY BOARD MUTATION)
       ===================================================== */

        // Save current state to allow rollback
        Piece captured = to.getPiece();

        // Variables used only if castling is attempted
        Cell rookFrom = null, rookTo = null;
        Piece rook = null;

        // Special case: Castling is a compound move (King + Rook)
        // Rook must also be moved during simulation to validate king safety
        if (piece instanceof King && Math.abs(from.getCol() - to.getCol()) == 2) {

            int direction = (to.getCol() > from.getCol()) ? 1 : -1; // defines white or black
            int rookCol = (direction == 1) ? 7 : 0; // 7 for white rook col, 0 for black rook col

            rookFrom = board.getCell(from.getRow(), rookCol); // fetch respective rook using from cell row and rookCol

            // Validate rook existence and type
            if (rookFrom == null || rookFrom.isEmpty()
                    || !(rookFrom.getPiece() instanceof Rook)) {
                System.out.println("Invalid castling!");
                return false;
            }

            rook = rookFrom.getPiece(); // after validation assign the rook
            // king hasn't moved yet (0,4) white king
            // moving rook next to the king
            rookTo = board.getCell(from.getRow(), from.getCol() + direction);

            // Simulate rook movement
            rookTo.setPiece(rook);
            rookFrom.setPiece(null);
        }

        // Simulate the main piece movement
        to.setPiece(piece);
        from.setPiece(null);

        // After simulation, verify that the player's king is not in check
        boolean kingInCheck = isKingInCheck(board, player.isWhite());

    /* =====================================================
       3️⃣ UNDO SIMULATION (ROLLBACK TO ORIGINAL STATE)
       ===================================================== */

        // Restore original piece positions
        from.setPiece(piece);
        to.setPiece(captured);

        // If castling was simulated, undo rook movement
        if (rookFrom != null) {
            rookFrom.setPiece(rook);
            rookTo.setPiece(null);
        }

        // Rule: A move that leaves the king in check is illegal
        if (kingInCheck) {
            System.out.println("Move leaves king in check!");
            return false;
        }

    /* =====================================================
       4️⃣ EXECUTE REAL MOVE (COMMIT LEGAL TRANSACTION)
       ===================================================== */

        // Permanently remove captured opponent piece
        if (captured != null) {
            captured.setKilled(true);
        }

        // Execute castling if applicable
        if (piece instanceof King && Math.abs(from.getCol() - to.getCol()) == 2) {
            handleCastling(from, to);
        }

        // Commit the main piece move
        to.setPiece(piece);
        from.setPiece(null);

        // Update movement flags (important for future castling rules)
        if (piece instanceof King) ((King) piece).setMoved();
        if (piece instanceof Rook) ((Rook) piece).setHasMoved(true);

        // Switch turn after successful move
        isWhiteTurn = !isWhiteTurn;

        // Optional: display board state
        board.printBoard();

    /* =====================================================
       5️⃣ CHECK / CHECKMATE LOGIC
       ===================================================== */

        boolean opponentIsWhite = !player.isWhite();
        if (isKingInCheck(board, opponentIsWhite)) {
            System.out.println("Check!");

            // Checkmate detection
            if (isCheckmate(opponentIsWhite)) {
                System.out.println("Checkmate! " + (player.isWhite() ? "White" : "Black") + " wins!");
                // Optional: terminate the game loop
                System.exit(0);
            }
        }

        return true;
    }

    /* =====================================================
   ✅ Checkmate Detection: returns true if player has no legal moves
   ===================================================== */
    public boolean isCheckmate(boolean isWhite) {

        // 2️⃣ Iterate over all pieces of this color
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Cell from = board.getCell(r, c);
                if (!from.isEmpty() && from.getPiece().isWhitePiece() == isWhite) {
                    Piece piece = from.getPiece();

                    // 2️⃣1️⃣ Try every target cell on board
                    for (int tr = 0; tr < 8; tr++) {
                        for (int tc = 0; tc < 8; tc++) {
                            Cell to = board.getCell(tr, tc);

                            // Skip if cannot move according to piece rules
                            if (!piece.canMove(board, from, to)) continue;

                            // Skip if capturing own piece
                            if (!to.isEmpty() && to.getPiece().isWhitePiece() == isWhite) continue;

                            // 2️⃣2️⃣ Simulate the move
                            Piece captured = to.getPiece();
                            to.setPiece(piece);
                            from.setPiece(null);

                            // 2️⃣3️⃣ Also simulate castling rook if needed
                            Piece rookCaptured = null;
                            Cell rookFrom = null, rookTo = null;
                            if (piece instanceof King && Math.abs(from.getCol() - to.getCol()) == 2) {
                                int dir = (to.getCol() > from.getCol()) ? 1 : -1;
                                int rookCol = (dir == 1) ? 7 : 0;
                                rookFrom = board.getCell(from.getRow(), rookCol);
                                rookTo = board.getCell(from.getRow(), from.getCol() + dir);
                                rookCaptured = rookTo.getPiece();
                                rookTo.setPiece(rookFrom.getPiece());
                                rookFrom.setPiece(null);
                            }

                            // 2️⃣4️⃣ Check if king is still in check after the move
                            boolean stillInCheck = isKingInCheck(board, isWhite);

                            // 2️⃣5️⃣ Undo move
                            from.setPiece(piece);
                            to.setPiece(captured);
                            if (rookFrom != null) {
                                rookFrom.setPiece(rookTo.getPiece());
                                rookTo.setPiece(rookCaptured);
                            }

                            // 2️⃣6️⃣ If any move resolves check, it's not checkmate
                            if (!stillInCheck) return false;
                        }
                    }
                }
            }
        }
        // 3️⃣ No move prevents check → checkmate
        return true;
    }

    /* =====================================================
       ✅ Castling Logic
       ===================================================== */
    private void handleCastling(Cell from, Cell to) {

        int direction = (to.getCol() > from.getCol()) ? 1 : -1;
        int rookFromCol = (direction == 1) ? 7 : 0;
        int rookToCol = from.getCol() + direction;

        Cell rookFrom = board.getCell(from.getRow(), rookFromCol);
        Cell rookTo = board.getCell(from.getRow(), rookToCol);

        Rook rook = (Rook) rookFrom.getPiece();

        rookTo.setPiece(rook);
        rookFrom.setPiece(null);

        rook.setHasMoved(true);
    }

    /* =====================================================
       ✅ Check Detection
       ===================================================== */
    public boolean isKingInCheck(Board board, boolean isWhiteKing) {
        Cell king = null;

        // Find king of the given color
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Cell cell = board.getCell(r, c);
                if (!cell.isEmpty()
                        && cell.getPiece() instanceof King
                        && cell.getPiece().isWhitePiece() == isWhiteKing) {
                    king = cell;
                }
            }
        }

        // If king not found, consider no check
        if (king == null) return false;

        // Iterate over all opponent pieces to see if any can attack king
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Cell cell = board.getCell(r, c);
                if (!cell.isEmpty() && cell.getPiece().isWhitePiece() != isWhiteKing) {
                    if (cell.getPiece().canMove(board, cell, king)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
