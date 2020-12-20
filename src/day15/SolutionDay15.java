package day15;

import java.util.HashMap;
import java.util.Map;

public class SolutionDay15 {

    public static void main(String[] args) {

        Map<Long, Long> numbers = initializeNumbers();
        long lastValue = 14;

        for (long i = 6; i < 30000000; i++) {
            if (i == 2020) System.out.format("SolutionA = %d\n" , lastValue);
            if (!numbers.containsKey(lastValue)) {
                numbers.put(lastValue, i);
                lastValue = 0;
            } else {
                long indexBefore = numbers.get(lastValue);
                numbers.put(lastValue, i);
                lastValue = i - indexBefore;
            }
        }
        System.out.format("SolutionB = %d\n" , lastValue);

    }

    private static Map<Long, Long> initializeNumbers() {
        Map<Long, Long> resultMap = new HashMap<>();
        resultMap.put(1L, 1L);
        resultMap.put(20L, 2L);
        resultMap.put(8L, 3L);
        resultMap.put(12L, 4L);
        resultMap.put(0L, 5L);
        return resultMap;
    }
}