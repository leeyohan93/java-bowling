package bowling.domain.state;

import bowling.domain.pin.Pins;

import java.util.Objects;

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
        if (secondFallenPins.count() > PIN_COUNT - firstFallenPins.count()) {
            throw new IllegalArgumentException("쓰러뜨릴 수 있는 Pin 숫자를 초과하였습니다.");
        }

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Trying)) return false;
        final Trying trying = (Trying) o;
        return Objects.equals(firstFallenPins, trying.firstFallenPins);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstFallenPins);
    }
}
