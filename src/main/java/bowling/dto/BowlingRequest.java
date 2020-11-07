package bowling.dto;

import bowling.domain.player.Player;

public class BowlingRequest {
    private final int fallenPin;
    private final String playerName;

    public BowlingRequest(final int fallenPin, final String playerName) {
        this.fallenPin = fallenPin;
        this.playerName = playerName;
    }

    public Player toPlayer() {
        return new Player(playerName);
    }

    public int getFallenPin() {
        return fallenPin;
    }

    public String getPlayerName() {
        return playerName;
    }
}
