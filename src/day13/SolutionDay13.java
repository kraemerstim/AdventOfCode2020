package day13;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static utility.AOCUtility.readFile;

public class SolutionDay13 {
    public static void main(String[] args) {
        var fileName = "resources/input/day13.txt";
        List<String> input = readFile(fileName);

        int targetNumber = Integer.parseInt(input.get(0));
        List<Integer> integers = Arrays.stream(input.get(1).split(",")).filter(s -> !s.equals("x")).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> integersWithX = Arrays.stream(input.get(1).split(",")).map(s -> s.equals("x") ? "-1" : s).map(Integer::parseInt).collect(Collectors.toList());

        int busID = integers.get(0);
        int minWaitTime = busID - (targetNumber % busID);
        for (int integer: integers) {
            int tempWaitTime = integer - (targetNumber % integer);
            if (tempWaitTime < minWaitTime) {
                minWaitTime = tempWaitTime;
                busID = integer;
            }
        }

        System.out.format("SolutionA = %d\n", busID * minWaitTime);

        long increment = integers.get(0);
        long currentTest = 0;
        long counter1 = 0;
        for (int workingIndex = 1; workingIndex < integers.size(); workingIndex++) {
            int busNumber = integers.get(workingIndex);
            int indexOfBus = integersWithX.indexOf(busNumber);
            while (!hasBusDelayOf(busNumber, indexOfBus, currentTest)) {
                currentTest += increment;
            }
            counter1 = currentTest;
            currentTest += increment;
            while (busNumber - (currentTest % busNumber) != indexOfBus % busNumber) {
                currentTest += increment;
            }
            increment = currentTest - counter1;
            if (workingIndex < integers.size()-1)
                currentTest = currentTest % increment;
        }


        System.out.format("SolutionB = %d\n", counter1);
    }

    private static boolean hasBusDelayOf(int busID, int wantedDelay, long time) {
        return busID - (time % busID) == wantedDelay % busID;
    }
}
