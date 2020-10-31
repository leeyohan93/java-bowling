package bowling.domain.frame;

import bowling.domain.state.*;

public class FinalFrame implements Frame {
    private static final String FRAME = "%-3s";
    private final int frameNumber;
    private final State state;

    public static FinalFrame init() {
        return new FinalFrame(10, new Ready());
    }

    public FinalFrame(final int frameNumber, final State state) {
        if (frameNumber != 10) {
            throw new IllegalArgumentException("마지막 프레임 넘버는 10 입니다.");
        }
        this.frameNumber = frameNumber;
        this.state = state;
    }

    @Override
    public boolean isFinished() {
        return state.isFinish();
    }

    @Override
    public Frame bowl(final int fallenPinCount) {
        if (isFinished()) {
            throw new IllegalStateException(String.format("이미 완료된 %d 프레임 입니다.", frameNumber));
        }

        State result = state.bowl(fallenPinCount);
        if (result instanceof Strike || result instanceof Spare) {
            return new FinalFrame(frameNumber, Bonus.start(result));
        }
        return new FinalFrame(frameNumber, result);
    }

    @Override
    public String print() {
        return String.format(FRAME, state.print());
    }
}
