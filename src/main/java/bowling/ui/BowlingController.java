package bowling.ui;

import bowling.application.BowlingService;
import bowling.dto.BowlingRequest;
import bowling.dto.BowlingResponse;

public class BowlingController {
    private final BowlingService bowlingService;

    public BowlingController(final BowlingService bowlingService) {
        this.bowlingService = bowlingService;
    }

    public BowlingResponse play(final BowlingRequest request) {
        return bowlingService.play(request);
    }
}
