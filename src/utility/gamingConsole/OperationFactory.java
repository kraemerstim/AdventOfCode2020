package utility.gamingConsole;

public class OperationFactory {
    public static Operation create(String operationType, int value) {
        switch (operationType) {
            case "nop":
                return new NOPOperation(value);
            case "jmp":
                return new JMPOperation(value);
            case "acc":
                return new ACCOperation(value);
            default:
                throw new IllegalArgumentException("WTF? there is no " + operationType + " instruction...");
        }
    }
}
