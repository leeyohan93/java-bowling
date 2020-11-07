package bowling.application;

import bowling.domain.frame.Frames;
import bowling.domain.pin.FallenPins;
import bowling.domain.player.Player;
import bowling.dto.BowlingRequest;
import bowling.dto.BowlingResponse;

public class BowlingService {
    private final Frames frames;

    public BowlingService() {
        frames = Frames.init();
    }

    public BowlingResponse play(BowlingRequest request) {
        Frames frames = this.frames.bowl(new FallenPins(request.getFallenPin()));
        return new BowlingResponse(frames, new Player(request.getPlayerName()));
    }
}
