package utility;

public class Move {

    private Player player;
    private Cell startCell;
    private Cell endCell;

    public Move(Player player, Cell startCell, Cell endCell) {
        this.player = player;
        this.startCell = startCell;
        this.endCell = endCell;
    }

    public boolean isValid() {
        if (startCell.isEmpty()) return false;
        if (!endCell.isEmpty() &&
                startCell.getPiece().isWhitePiece() ==
                        endCell.getPiece().isWhitePiece()) {
            return false;
        }
        return true;
    }

    public Player getPlayer() {
        return player;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }
}
