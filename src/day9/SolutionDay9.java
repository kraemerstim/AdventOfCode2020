package day9;

import static utility.AOCUtility.readFile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SolutionDay9 {
    private static int PREAMBLE = 25;

    public static void main(String[] args) {
        var fileName = "resources/input/day9.txt";
        List<Long> input = readFile(fileName).stream().map(Long::parseLong).collect(Collectors.toList());

        var possibleNumbers = createMap(input);

        Long solutionA = getNumberNotMatchingRule(input, possibleNumbers);
        System.out.println("solutionA = " + solutionA);

        System.out.println("solutionB = " + getSolutionB(input, solutionA));
    }

    private static Long getNumberNotMatchingRule(List<Long> inputValues, Map<Integer, Set<Long>> possibleNumbersForEveryIndex) {
        Long currentNumber = 0L;
        for (int i = PREAMBLE; i < inputValues.size(); i++) {
            currentNumber = inputValues.get(i);
            var currentSetOfPossibleValues = possibleNumbersForEveryIndex.get(i);
            if (!currentSetOfPossibleValues.contains(currentNumber)) {
                break;
            }
        }
        return currentNumber;
    }

    private static Map<Integer, Set<Long>> createMap(List<Long> integerList) {
        Map<Integer, Set<Long>> resultMap = new HashMap<>();

        for (int i = 0; i < integerList.size() - PREAMBLE; i++) {
            for (int j = i + 1; j <= i + PREAMBLE; j++) {
                if (j < integerList.size()) {
                    for (int k = j + 1; k <= i + PREAMBLE; k++) {
                        Set<Long> currentList = resultMap.getOrDefault(k, new HashSet<>());
                        currentList.add(integerList.get(i) + integerList.get(j));
                        resultMap.putIfAbsent(k, currentList);
                    }
                }
            }
        }

        return resultMap;
    }

    private static Long getSolutionB(List<Long> integerList, Long numberToFind) {
        for (int i = 0; i < integerList.size(); i++) {
            long sum = 0;
            long lowestNumber = integerList.get(i);
            long highestNumber = integerList.get(i);
            for (int j = i; j < integerList.size() && sum < numberToFind; j++) {
                Long currentNumber = integerList.get(j);
                sum += currentNumber;
                highestNumber = Math.max(currentNumber, highestNumber);
                lowestNumber = Math.min(currentNumber, lowestNumber);
            }
            if (sum == numberToFind) {
                return highestNumber + lowestNumber;
            }
        }
        return -1L;
    }
}
