package bowling.domain.state;

import bowling.domain.pin.Pins;

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
}
