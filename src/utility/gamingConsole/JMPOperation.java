package utility.gamingConsole;

public class JMPOperation implements Operation {
    private int pointerOffset;

    JMPOperation(int pointerOffset) {
        this.pointerOffset = pointerOffset;
    }

    @Override
    public void execute(ConsoleState state) {
        int pointer = state.getPosition();
        pointer += pointerOffset;
        state.setPosition(pointer);
    }

    @Override
    public int getValue() {
        return pointerOffset;
    }
}
