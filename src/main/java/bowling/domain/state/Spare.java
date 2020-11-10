package bowling.domain.state;

import bowling.domain.pin.Pins;

import java.util.Objects;

import static bowling.domain.state.Miss.GUTTER_PIN_COUNT;
import static bowling.domain.state.Miss.GUTTER_SYMBOL;

public class Spare extends Finished {
    public static final String SYMBOL = "/";
    private static final String PRINT_FORM = "%s|%s";
    private final int firstFallenPinCount;

    public static Spare from(final Pins firstFallenPins) {
        return new Spare(firstFallenPins.count());
    }

    public Spare(final int firstFallenPinCount) {
        this.firstFallenPinCount = firstFallenPinCount;
    }

    @Override
    public String print() {
        String spareFormat = String.format(PRINT_FORM, firstFallenPinCount, SYMBOL);
        return convertGutter(spareFormat);
    }

    private String convertGutter(final String spareFormat) {
        return spareFormat.replaceAll(String.valueOf(GUTTER_PIN_COUNT), GUTTER_SYMBOL);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Spare)) return false;
        final Spare spare = (Spare) o;
        return firstFallenPinCount == spare.firstFallenPinCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstFallenPinCount);
    }
}
