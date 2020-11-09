package bowling.domain.state;

import bowling.domain.pin.Pins;

import static bowling.domain.pin.Pins.PIN_COUNT;

public class Trying implements State {
    private static final String PRINT_FORM = "%s";
    private static final int GUTTER_NUMBER = 0;
    private static final String GUTTER_SYMBOL = "-";
    private final int firstFallen;

    public static Trying from(final Pins fallenPins) {
        return new Trying(fallenPins.count());
    }

    private Trying(final int firstFallen) {
        this.firstFallen = firstFallen;
    }

    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public State bowl(final Pins pins) {
        int secondFallen = pins.count();
        if (firstFallen + secondFallen == PIN_COUNT) {
            return new Spare(firstFallen);
        }
        return new Miss(firstFallen, secondFallen);
    }

    @Override
    public String print() {
        if (firstFallen == GUTTER_NUMBER) {
            return String.format(PRINT_FORM, GUTTER_SYMBOL);
        }
        return String.format(PRINT_FORM, firstFallen);
    }
}
