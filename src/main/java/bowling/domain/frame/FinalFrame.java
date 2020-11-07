package bowling.domain.frame;

import bowling.domain.pin.Pins;
import bowling.domain.state.*;

import java.util.Objects;

public class FinalFrame implements Frame {
    public static final int FINAL_FRAME_NUMBER = 10;
    private final State state;

    public static FinalFrame init() {
        return new FinalFrame(new Ready());
    }

    public FinalFrame(final State state) {
        this.state = state;
    }

    @Override
    public boolean isFinished() {
        return state.isFinish();
    }

    @Override
    public Frame bowl(final Pins pins) {
        if (isFinished()) {
            throw new IllegalStateException("이미 완료된 프레임 입니다.");
        }

        State result = state.bowl(pins);
        if (result instanceof Strike || result instanceof Spare) {
            return new FinalFrame(Bonus.start(result));
        }
        return new FinalFrame(result);
    }

    @Override
    public String print() {
        return String.format(FRAME, state.print());
    }

    @Override
    public int number() {
        return FINAL_FRAME_NUMBER;
    }

    @Override
    public Frame next() {
        throw new IllegalStateException("마지막 프레임입니다.");
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof FinalFrame)) return false;
        final FinalFrame that = (FinalFrame) o;
        return Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
