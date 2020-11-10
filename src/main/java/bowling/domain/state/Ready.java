package bowling.domain.state;

import bowling.domain.pin.Pins;

import static bowling.domain.state.Strike.STRIKE_PIN_COUNT;

public class Ready implements State {
    private static final String PRINT_FORM = "";

    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public State bowl(final Pins fallenPins) {
        int fallenPinCount = fallenPins.count();
        if (fallenPinCount == STRIKE_PIN_COUNT) {
            return new Strike();
        }
        return Trying.from(fallenPins);
    }

    @Override
    public String print() {
        return PRINT_FORM;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Ready;
    }

    @Override
    public int hashCode() {
        return Ready.class.hashCode();
    }
}
