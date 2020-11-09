package bowling.domain.state;

import bowling.domain.pin.Pins;

import static bowling.domain.pin.Pins.PIN_COUNT;

public class Trying implements State {
    private static final String PRINT_FORM = "%s";
    private static final int GUTTER_NUMBER = 0;
    private static final String GUTTER_SYMBOL = "-";
    private final Pins firstFallenPins;

    public static Trying from(final Pins fallenPins) {
        return new Trying(fallenPins);
    }

    public Trying(final Pins firstFallenPins) {
        this.firstFallenPins = firstFallenPins;
    }

    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public State bowl(final Pins secondFallenPins) {
        if (firstFallenPins.count() + secondFallenPins.count() == PIN_COUNT) {
            return Spare.from(firstFallenPins);
        }
        return Miss.of(firstFallenPins, secondFallenPins);
    }

    @Override
    public String print() {
        int firstFallen = firstFallenPins.count();
        return firstFallen == GUTTER_NUMBER ? String.format(PRINT_FORM, GUTTER_SYMBOL) :
                String.format(PRINT_FORM, firstFallen);
    }
}
