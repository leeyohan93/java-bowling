package bowling.domain.state;

import bowling.domain.pin.Pins;

import java.util.Objects;

public class Miss extends Finished {
    private static final String PRINT_FORM = "%s|%s";
    public static final int GUTTER_PIN_COUNT = 0;
    public static final String GUTTER_SYMBOL = "-";
    private final int firstFallenPinCount;
    private final int secondFallenPinCount;

    public static State of(final Pins firstFallenPins, final Pins secondFallenPins) {
        return new Miss(firstFallenPins.count(), secondFallenPins.count());
    }

    private Miss(final int firstFallenPinCount, final int secondFallenPinCount) {
        this.firstFallenPinCount = firstFallenPinCount;
        this.secondFallenPinCount = secondFallenPinCount;
    }

    @Override
    public String print() {
        String missFormat = String.format(PRINT_FORM, firstFallenPinCount, secondFallenPinCount);
        return convertGutter(missFormat);
    }

    private String convertGutter(final String sparePrint) {
        return sparePrint.replaceAll(String.valueOf(GUTTER_PIN_COUNT), GUTTER_SYMBOL);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Miss)) return false;
        final Miss miss = (Miss) o;
        return firstFallenPinCount == miss.firstFallenPinCount &&
                secondFallenPinCount == miss.secondFallenPinCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstFallenPinCount, secondFallenPinCount);
    }
}
