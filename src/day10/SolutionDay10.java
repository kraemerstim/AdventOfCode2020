package day10;

import static utility.AOCUtility.readFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SolutionDay10 {

    public static void main(String[] args) {
        var fileName = "resources/input/day10.txt";
        List<Integer> input = readFile(fileName).stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
        input.add(0, 0);
        input.add(input.get(input.size() - 1) + 3);

        int[] counters = {0, 0, 0};
        for (int i = 0; i < input.size() - 1; i++) {
            counters[input.get(i + 1) - input.get(i) - 1]++;
        }

        int solution = counters[0] * counters[2];
        System.out.format("SolutionA = %d\n", solution);

        Map<Integer, Long> waysToGetTo = new HashMap<>();
        Long numberOfWaysToGetToNumber = 0L;
        waysToGetTo.put(0, 1L);
        for (int i = 1; i < input.size(); i++) {
            int number = input.get(i);
            numberOfWaysToGetToNumber = 0L;
            for (int j = number - 3; j < number; j++) {
                Long previousResult = waysToGetTo.get(j);
                if (previousResult != null) {
                    numberOfWaysToGetToNumber += previousResult;

                }
            }
            waysToGetTo.put(number, numberOfWaysToGetToNumber);
        }

        System.out.format("SolutionB = %d\n", numberOfWaysToGetToNumber);

    }
}
