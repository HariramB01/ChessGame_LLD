package utility;

import helper.BoardHelper;

public class Board {

    private final Cell[][] chessBoard;
    private final int boardSize;
    private final BoardHelper boardHelper;

    public Board(int boardSize) throws IllegalAccessException {
        if (boardSize <= 0) {
            throw new IllegalArgumentException("Board size must be greater than 0");
        }

        this.boardSize = boardSize;
        this.chessBoard = new Cell[boardSize][boardSize];
        this.boardHelper = new BoardHelper();
        initializeBoard();
    }

    private void initializeBoard() throws IllegalAccessException {
        boardHelper.setNonPawnPieceRow(chessBoard, 0, true);
        boardHelper.setPawnPieceRow(chessBoard, 1, boardSize, true);

        boardHelper.setNonPawnPieceRow(chessBoard, boardSize - 1, false);
        boardHelper.setPawnPieceRow(chessBoard, boardSize - 2, boardSize, false);


        for (int row = 2; row < boardSize - 2; row++) {
            for (int col = 0; col < boardSize; col++) {
                chessBoard[row][col] = new Cell(row, col, null);
            }
        }
    }

    public boolean isInsideBoard(Cell cell) {
        return cell.getRow() >= 0 && cell.getRow() < boardSize &&
                cell.getCol() >= 0 && cell.getCol() < boardSize;
    }

    public Cell getCell(int row, int col) {
        if (!isInsideBoard(new Cell(row, col))) return null;
        return chessBoard[row][col];
    }

    public Piece getPiece(Cell cell) {
        if (!isInsideBoard(cell)) return null;
        return chessBoard[cell.getRow()][cell.getCol()].getPiece();
    }

    public boolean isSquareUnderAttack(Cell target, boolean isWhiteKing) {

        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {

                Cell fromCell = chessBoard[r][c];
                if (fromCell == null || fromCell.isEmpty()) continue;

                Piece piece = fromCell.getPiece();

                /* only opponent pieces */
                if (piece.isWhitePiece() == isWhiteKing) continue;

                if (piece.canMove(this, fromCell, target)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void printBoard() {
        System.out.print("  "); // padding for row numbers
        for (int c = 0; c < boardSize; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int r = boardSize - 1; r >= 0; r--) {
            System.out.print(r + " "); // row number
            for (int c = 0; c < boardSize; c++) {
                Cell cell = chessBoard[r][c];
                if (cell == null || cell.isEmpty()) {
                    System.out.print(". ");
                } else {
                    System.out.print(cell.getPiece().getClass().getSimpleName().charAt(0) + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }




}
