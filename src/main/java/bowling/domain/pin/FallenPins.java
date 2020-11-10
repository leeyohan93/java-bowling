package bowling.domain.pin;

import java.util.Objects;

public class FallenPins implements Pins {
    private final int fallen;

    public FallenPins(final int fallen) {
        if (fallen < 0 || fallen > PIN_COUNT) {
            throw new IllegalArgumentException(String.format("쓰러뜨린 핀 개수가 잘못 되었습니다. %d", fallen));
        }
        this.fallen = fallen;
    }

    @Override
    public int count() {
        return fallen;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof FallenPins)) return false;
        final FallenPins that = (FallenPins) o;
        return fallen == that.fallen;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fallen);
    }
}
