package day8;

import utility.gamingConsole.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utility.AOCUtility.readFile;

public class SolutionDay8 {
    public static void main(String[] args) {
        final String INSTRUCTION_PATTERN = "^(\\S{3}) ([+-]\\d*)$";

        var fileName = "resources/input/day8.txt";
        List<String> input = readFile(fileName);

        List<Operation> operationList = new ArrayList<>();

        Pattern instructionPattern = Pattern.compile(INSTRUCTION_PATTERN);
        for (String instructionString : input) {
            Matcher matcher = instructionPattern.matcher(instructionString);
            if (matcher.matches()) {
                int instructionValue = Integer.parseInt(matcher.group(2));
                String instructionType = matcher.group(1);
                operationList.add(OperationFactory.create(instructionType, instructionValue));
            }
        }

        Set<Integer> executedPointers = new HashSet<>();
        List<Integer> possibleErrorPositions = new ArrayList<>();

        ConsoleState state = new ConsoleState();

        Integer pointer = 0;

        while (!executedPointers.contains(pointer)) {
            executedPointers.add(pointer);
            if (!(operationList.get(pointer) instanceof ACCOperation)) {
                possibleErrorPositions.add(pointer);
            }
            operationList.get(pointer).execute(state);
            pointer = state.getPosition();
        }

        System.out.println("SolutionA: " + state.getValue());

        for (Integer fuckup : possibleErrorPositions) {
            List<Operation> testOperations = new ArrayList<>(operationList);
            changeOperation(testOperations, fuckup);
            executedPointers.clear();

            pointer = 0;
            state.reset();

            while (pointer < testOperations.size() && !executedPointers.contains(pointer)) {
                executedPointers.add(pointer);
                testOperations.get(pointer).execute(state);
                pointer = state.getPosition();
            }

            if (pointer == testOperations.size()) System.out.println("SolutionB: " + state.getValue());
        }

    }

    private static void changeOperation(List<Operation> operationList, Integer operationIndex) {
        Operation operation = operationList.get(operationIndex);
        if (operation instanceof NOPOperation) {
            operationList.set(operationIndex, OperationFactory.create("jmp", operation.getValue()));
        } else {
            operationList.set(operationIndex, OperationFactory.create("nop", operation.getValue()));
        }
    }
}