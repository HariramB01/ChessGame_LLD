import handler.ChessGame;
import handler.ChessGameManager;
import utility.Board;
import utility.Cell;
import utility.Player;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        ChessGameManager manager = new ChessGameManager();

        Player white = new Player("White", true, null);
        Player black = new Player("Black", false, null);

        String gameId = manager.createGame(white, black);

        Board board = manager.getGame(gameId).getBoard();
        board.printBoard();

        Scanner scanner = new Scanner(System.in);
        ChessGame game = manager.getGame(gameId);

        while (true) {

            Player currentPlayer = game.isWhiteTurn() ? white : black;

            System.out.println(currentPlayer.getPlayerName() +
                    "'s move (format: fromRow fromCol toRow toCol):");

            int fr = scanner.nextInt();
            int fc = scanner.nextInt();
            int tr = scanner.nextInt();
            int tc = scanner.nextInt();

            Cell from = board.getCell(fr, fc);
            Cell to = board.getCell(tr, tc);

            if (from == null || to == null) {
                System.out.println("Invalid coordinates!");
                continue;
            }

            // 🔐 THREAD-SAFE MOVE
            if (!manager.makeMove(gameId, currentPlayer, from, to)) {
                System.out.println("Try again.");
            }
        }
    }
}



//
//
//import handler.ChessGame;
//import utility.Board;
//import utility.Cell;
//import utility.Player;
//
//public class Main {
//
//    public static void main(String[] args) throws Exception {
//
//        Board board = new Board(8);
//        Player white = new Player("White", true, null);
//        Player black = new Player("Black", false, null);
//
//        ChessGame game = new ChessGame(board, white, black);
//
//        System.out.println("INITIAL BOARD");
//        board.printBoard();
//
//        // 1️⃣ Illegal move (empty square)
//        game.makeMove(white, board.getCell(3, 3), board.getCell(3, 4));
//
//        // 2️⃣ Illegal pawn diagonal
//        game.makeMove(white, board.getCell(1, 4), board.getCell(2, 5));
//
//        // 3️⃣ e2 -> e4
//        game.makeMove(white, board.getCell(1, 4), board.getCell(3, 4));
//
//        // 4️⃣ e7 -> e5
//        game.makeMove(black, board.getCell(6, 4), board.getCell(4, 4));
//
//        // 5️⃣ g1 -> f3
//        game.makeMove(white, board.getCell(0, 6), board.getCell(2, 5));
//
//        // 6️⃣ b8 -> c6
//        game.makeMove(black, board.getCell(7, 1), board.getCell(5, 2));
//
//        // 7️⃣ f1 -> c4
//        game.makeMove(white, board.getCell(0, 5), board.getCell(3, 2));
//
//        // 8️⃣ g8 -> f6
//        game.makeMove(black, board.getCell(7, 6), board.getCell(5, 5));
//
//        // 9️⃣ d2 -> d3
//        game.makeMove(white, board.getCell(1, 3), board.getCell(2, 3));
//
//        // 🔟 f8 -> c5
//        game.makeMove(black, board.getCell(7, 5), board.getCell(4, 2));
//
//        // 11️⃣ CASTLING (white king side)
//        game.makeMove(white, board.getCell(0, 4), board.getCell(0, 6));
//
//        // 12️⃣ d7 -> d6
//        game.makeMove(black, board.getCell(6, 3), board.getCell(5, 3));
//
//        // 13️⃣ c1 -> g5
//        game.makeMove(white, board.getCell(0, 2), board.getCell(4, 6));
//
//        // 14️⃣ h7 -> h6
//        game.makeMove(black, board.getCell(6, 7), board.getCell(5, 7));
//
//        // 15️⃣ g5 x f6 (capture)
//        game.makeMove(white, board.getCell(4, 6), board.getCell(5, 5));
//
//        // 16️⃣ Qd8 -> e7
//        game.makeMove(black, board.getCell(7, 3), board.getCell(6, 4));
//
//        // 17️⃣ Bc4 -> f7 (CHECK)
//        game.makeMove(white, board.getCell(3, 2), board.getCell(6, 5));
//
//        // 18️⃣ Ke8 -> d8
//        game.makeMove(black, board.getCell(7, 4), board.getCell(7, 3));
//
//        // 19️⃣ Qd1 -> e2
//        game.makeMove(white, board.getCell(0, 3), board.getCell(1, 4));
//
//        // 20️⃣ Qe7 x f7 (capture)
//        game.makeMove(black, board.getCell(6, 4), board.getCell(6, 5));
//
//        // 21️⃣ White rook restricts king
//        game.makeMove(white, board.getCell(0, 7), board.getCell(7, 7));
//
//        // 22️⃣ Black king moves
//        game.makeMove(black, board.getCell(7, 3), board.getCell(7, 2));
//
//        // 23️⃣ White king supports rook
//        game.makeMove(white, board.getCell(0, 6), board.getCell(1, 6));
//
//        // 24️⃣ CHECKMATE
//        game.makeMove(white, board.getCell(7, 7), board.getCell(7, 2));
//
//        System.out.println("GAME SIMULATION COMPLETED: CHECKMATE!");
//    }
//}
//
