package bowling.domain.frame;

import bowling.domain.pin.FallenPins;
import bowling.domain.state.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FinalFrameTest {
    private FinalFrame finalFrame;

    @BeforeEach
    void setUp() {
        finalFrame = FinalFrame.init();
    }

    @Test
    @DisplayName("FinalFrame 초기화")
    void init() {
        assertThat(finalFrame).isInstanceOf(Frame.class);
    }

    @Test
    @DisplayName("FinalFrame 프레임 번호는 State에 관계 없이 항상 10")
    void frameNumberException() {
        assertThat(finalFrame).isInstanceOf(Frame.class);
        assertThat(new FinalFrame(new Ready()).number()).isEqualTo(10);
        assertThat(new FinalFrame(new Strike()).number()).isEqualTo(10);
    }

    @Test
    @DisplayName("FinalFrame 프레임의 종료 여부 = 볼을 굴릴 수 있느냐")
    void isFinished() {
        FinalFrame finishedFrame = new FinalFrame(new Finished() {
            @Override
            public String print() {
                return "";
            }
        });

        assertThat(finalFrame.isFinished()).isFalse();
        assertThat(finishedFrame.isFinished()).isTrue();
    }

    @ParameterizedTest
    @DisplayName("FinalFrame Trying")
    @ValueSource(ints = {0, 1, 5})
    void trying(int pinCount) {
        Frame target = finalFrame.bowl(new FallenPins(pinCount));
        FinalFrame expectedFrame = new FinalFrame(new Trying(new FallenPins(pinCount)));
        assertThat(target.equals(expectedFrame)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("FinalFrame 미스")
    @CsvSource(value = {"0,0", "9,0", "1,5", "5,4"})
    void miss(int firstPinCount, int secondPinCount) {
        Frame target = finalFrame.bowl(new FallenPins(firstPinCount)).bowl(new FallenPins(secondPinCount));
        FinalFrame expectedFrame = new FinalFrame(Miss.of(new FallenPins(firstPinCount), new FallenPins(secondPinCount)));
        assertThat(target.equals(expectedFrame)).isTrue();
    }

    @Test
    @DisplayName("FinalFrame Spare 시 Bonus 시작")
    void bonusFromSpare() {
        Frame spareFrame = finalFrame.bowl(new FallenPins(1)).bowl(new FallenPins(9));
        FinalFrame expectedFrame = new FinalFrame(Bonus.start(new Spare(1)));
        assertThat(spareFrame).isEqualTo(expectedFrame);
    }

    @Test
    @DisplayName("FinalFrame Strike 시 Bonus 시작")
    void bonusFromStrike() {
        Frame strikeFrame = finalFrame.bowl(new FallenPins(10));
        FinalFrame expectedFrame = new FinalFrame(Bonus.start(new Strike()));
        assertThat(strikeFrame).isEqualTo(expectedFrame);
    }

    @Test
    @DisplayName("FinalFrame Bonus 시 한번 더 투구 가능")
    void bonusBowl() {
        Frame strikeFrame = finalFrame.bowl(new FallenPins(10));
        Frame spareFrame = finalFrame.bowl(new FallenPins(1)).bowl(new FallenPins(9));

        assertThat(strikeFrame.bowl(new FallenPins(3))).isNotNull();
        assertThat(spareFrame.bowl(new FallenPins(3))).isNotNull();
    }

    @Test
    @DisplayName("FinalFrame Strike 두번 연속일 경우 마지막 한번 더 투구 가능")
    void oneMore() {
        Frame strikeFrame = finalFrame.bowl(new FallenPins(10)).bowl(new FallenPins(10));
        Frame spareFrame = finalFrame.bowl(new FallenPins(1)).bowl(new FallenPins(9));

        assertThat(strikeFrame.bowl(new FallenPins(10))).isNotNull();
        // spareFrame은 한번까지 보너스 가능
        assertThrows(IllegalStateException.class, ()-> spareFrame.bowl(new FallenPins(3)).bowl(new FallenPins(5)));
    }

    @Test
    @DisplayName("FinalFrame print")
    void print() {
        Frame frame = FinalFrame.init();
        assertThat(frame.bowl(() -> 0).print().trim()).isEqualTo("-");                          // Gutter
        assertThat(frame.bowl(() -> 10).print().trim()).isEqualTo("X");                         // Strike
        assertThat(frame.bowl(() -> 5).print().trim()).isEqualTo("5");                          // Trying
        assertThat(frame.bowl(() -> 5).bowl(() -> 3).print()).isEqualTo("5|3");                 // Miss
        assertThat(frame.bowl(() -> 5).bowl(() -> 5).print()).isEqualTo("5|/");                 // Spare
        assertThat(frame.bowl(() -> 0).bowl(() -> 0).print()).isEqualTo("-|-");                 // Gutter
        assertThat(frame.bowl(() -> 10).bowl(() -> 0).print()).isEqualTo("X|-");                // Strike Gutter
        assertThat(frame.bowl(() -> 10).bowl(() -> 10).print()).isEqualTo("X|X");               // Strike Strike
        assertThat(frame.bowl(() -> 10).bowl(() -> 5).print()).isEqualTo("X|5");                // Strike Number
        assertThat(frame.bowl(() -> 5).bowl(() -> 5).bowl(() -> 0).print()).isEqualTo("5|/|-");         // Spare Gutter
        assertThat(frame.bowl(() -> 5).bowl(() -> 5).bowl(() -> 10).print()).isEqualTo("5|/|X");        // Spare Strike
        assertThat(frame.bowl(() -> 5).bowl(() -> 5).bowl(() -> 5).print()).isEqualTo("5|/|5");         // Spare Number
        System.out.println(frame.bowl(() -> 0).print());
        System.out.println(frame.bowl(() -> 10).print());
        System.out.println(frame.bowl(() -> 5).print());
        System.out.println(frame.bowl(() -> 5).bowl(() -> 3).print());
        System.out.println(frame.bowl(() -> 5).bowl(() -> 5).print());
        System.out.println(frame.bowl(() -> 0).bowl(() -> 0).print());
        System.out.println(frame.bowl(() -> 5).bowl(() -> 5).bowl(() -> 0).print());
        System.out.println(frame.bowl(() -> 5).bowl(() -> 5).bowl(() -> 10).print());
        System.out.println(frame.bowl(() -> 5).bowl(() -> 5).bowl(() -> 5).print());
    }

    @Test
    @DisplayName("완료된 프레임에 볼을 굴릴 경우 예외 발생")
    void bowlException() {
        FinalFrame finishedFrame = new FinalFrame(new Finished() {
            @Override
            public String print() {
                return "";
            }
        });

        assertThrows(IllegalStateException.class, () -> finishedFrame.bowl(() -> 1));
    }

    @Test
    @DisplayName("FinalFrame의 frameNumber는 10 이다.")
    void number() {
        assertThat(finalFrame.number()).isEqualTo(10);
    }

    @Test
    @DisplayName("FinalFrame 은 다음 프레임을 만들 수 없다. 예외 발생")
    void exceptNextFrame() {
        assertThrows(IllegalStateException.class, () -> finalFrame.next());
    }
}
