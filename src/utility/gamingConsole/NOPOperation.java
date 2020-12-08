package utility.gamingConsole;

public class NOPOperation implements Operation {
    private int uselessOffset;

    NOPOperation(int uselessOffset) {
        this.uselessOffset = uselessOffset;
    }

    @Override
    public void execute(ConsoleState state) {
        int pointer = state.getPosition();
        pointer += 1;
        state.setPosition(pointer);
    }

    @Override
    public int getValue() {
        return uselessOffset;
    }
}
