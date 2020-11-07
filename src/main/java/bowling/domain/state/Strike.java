package bowling.domain.state;

public class Strike extends Finished {
    public static final int STRIKE_PIN_COUNT= 10;

    public static final String SYMBOL = "X";

    @Override
    public String print() {
        return SYMBOL;
    }
}
