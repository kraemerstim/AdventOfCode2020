package utility.gamingConsole;

public class ACCOperation implements Operation {
    private int valueOffset;

    ACCOperation(int valueOffset) {
        this.valueOffset = valueOffset;
    }

    @Override
    public void execute(ConsoleState state) {
        int pointer = state.getPosition();
        int value = state.getValue();
        pointer += 1;
        value += valueOffset;
        state.setPosition(pointer);
        state.setValue(value);
    }

    @Override
    public int getValue() {
        return valueOffset;
    }
}
