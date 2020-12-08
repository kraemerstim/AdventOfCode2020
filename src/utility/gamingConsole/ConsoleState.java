package utility.gamingConsole;

public class ConsoleState {
    private int position;
    private int value;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public int getValue() {
        return value;
    }

    public void reset() {
        position = 0;
        value = 0;
    }
}
