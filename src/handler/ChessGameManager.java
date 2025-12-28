package handler;

import utility.Board;
import utility.Cell;
import utility.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ChessGameManager {

    // Thread-safe registry of games
    private final ConcurrentHashMap<String, ChessGame> games =
            new ConcurrentHashMap<>();

    public String createGame(Player white, Player black) throws IllegalAccessException {
        Board board = new Board(8);
        ChessGame game = new ChessGame(board, white, black);

        String gameId = UUID.randomUUID().toString();
        games.put(gameId, game);

        return gameId;
    }

    public ChessGame getGame(String gameId) {
        return games.get(gameId);
    }

    public boolean makeMove(String gameId, Player player, Cell from, Cell to) {
        ChessGame game = games.get(gameId);

        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        // 🔐 CRITICAL SECTION
        synchronized (game) {
            return game.makeMove(player, from, to);
        }
    }
}

