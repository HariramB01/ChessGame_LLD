package utility;

import strategy.PlayerStrategy;

public class Player {
    private String playerName;
    private boolean isWhite;
    private PlayerStrategy playerStrategy;

    public Player(String playerName, boolean isWhite, PlayerStrategy playerStrategy) {
        this.playerName = playerName;
        this.isWhite = isWhite;
        this.playerStrategy = playerStrategy;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public PlayerStrategy getPlayerStrategy() {
        return playerStrategy;
    }

}
