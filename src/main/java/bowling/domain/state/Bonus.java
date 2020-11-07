package bowling.domain.state;

import bowling.domain.pin.Pins;

import static bowling.domain.state.Strike.STRIKE_PIN_COUNT;

public class Bonus implements State {
    private static final String PRINT_FORM = "%s|%s";
    private static final int DEFAULT_LEFT = 1;
    private static final int DEFAULT_PIN = 0;
    private final State preState;
    private final int leftCount;
    private final int fallenPinCount;

    public static Bonus start(final State preState) {
        return new Bonus(preState, DEFAULT_LEFT, DEFAULT_PIN);
    }

    private Bonus(final State preState, final int leftCount, final int fallenPinCount) {
        if (!(preState instanceof Strike) && !(preState instanceof Spare) && !(preState instanceof Bonus)) {
            throw new IllegalArgumentException("Bonus는 스트라이크나 스페어일때만 가능합니다.");
        }
        this.preState = preState;
        this.leftCount = leftCount;
        this.fallenPinCount = fallenPinCount;
    }

    @Override
    public boolean isFinish() {
        return leftCount == 0;
    }

    @Override
    public State bowl(final Pins pins) {
        if (isFinish()) {
            throw new IllegalStateException("프레임이 완료되어 볼을 던질 수 없습니다.");
        }

        if (pins.count() == STRIKE_PIN_COUNT && (preState instanceof Strike)) {
            Bonus bonus = new Bonus(preState, leftCount - 1, pins.count());
            return new Bonus(bonus, DEFAULT_LEFT, DEFAULT_PIN);
        }

        return new Bonus(preState, leftCount - 1, pins.count());
    }

    @Override
    public String print() {
        if (leftCount > 0) {
            return preState.print();
        }

        if (fallenPinCount == Strike.STRIKE_PIN_COUNT) {
            return String.format(PRINT_FORM, preState.print(), Strike.SYMBOL);
        }

        if (fallenPinCount == Miss.GUTTER_PIN_COUNT) {
            return String.format(PRINT_FORM, preState.print(), Miss.GUTTER_SYMBOL);
        }

        return String.format(PRINT_FORM, preState.print(), fallenPinCount);
    }
}
