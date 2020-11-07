package bowling.domain.frame;

import bowling.domain.pin.Pins;

public interface Frame {

    String FRAME = "%-3s";

    boolean isFinished();

    Frame bowl(Pins pins);

    String print();

    int number();

    Frame next();
}
