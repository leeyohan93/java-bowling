package bowling.domain.state;

import bowling.domain.pin.Pins;

import static bowling.domain.pin.Pins.PIN_COUNT;

public class Ready implements State {
    private static final String PRINT_FORM = "";

    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public State bowl(final Pins pins) {
        int fallenPinCount = pins.count();
        if (fallenPinCount == PIN_COUNT) {
            return new Strike();
        }
        return new Trying(fallenPinCount);
    }

    @Override
    public String print() {
        return PRINT_FORM;
    }
}
