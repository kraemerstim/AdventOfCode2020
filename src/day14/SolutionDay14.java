package day14;

import static utility.AOCUtility.readFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolutionDay14 {
    public static void main(String[] args) {
        final String PATTERN_MASK = "^mask = (\\S*)$";
        final String PATTERN_MEM = "^mem\\[(\\d*)] = (\\d*)$";

        Pattern maskPattern = Pattern.compile(PATTERN_MASK);
        Pattern memPattern = Pattern.compile(PATTERN_MEM);

        var fileName = "resources/input/day14.txt";
        List<String> input = readFile(fileName);

        Long andMask = 0L;
        Long orMask = 0L;

        Map<Long, Long> memory = new HashMap<>();
        for (String command : input) {
            Matcher matcher = maskPattern.matcher(command);
            if (matcher.matches()) {
                andMask = createAndMask(matcher.group(1));
                orMask = createOrMask(matcher.group(1));
            } else {
                matcher = memPattern.matcher(command);
                if (matcher.matches()) {
                    Long numberToInsert = Long.parseLong(matcher.group(2));
                    Long register = Long.parseLong(matcher.group(1));

                    numberToInsert = numberToInsert | orMask;
                    numberToInsert = numberToInsert & andMask;

                    memory.put(register, numberToInsert);
                }
            }
        }

        Long result = memory.values().stream().mapToLong(Long::longValue).sum();
        System.out.format("SolutionA = %d\n", result);

        memory = new HashMap<>();
        String currentMask = "";
        for (String line : input) {
            Matcher matcher = maskPattern.matcher(line);
            if (matcher.matches()) {
                currentMask = matcher.group(1);
            } else {
                matcher = memPattern.matcher(line);
                if (matcher.matches()) {
                    Long register = Long.parseLong(matcher.group(1));
                    Long numberToInsert = Long.parseLong(matcher.group(2));

                    List<Long> registerList = getEveryAffectedRegister(currentMask, register);

                    for (Long currentRegister : registerList) {
                        memory.put(currentRegister, numberToInsert);
                    }
                }
            }
        }

        result = 0L;
        for (var key : memory.keySet()) {
            result += memory.get(key);
        }

        System.out.format("SolutionB = %d\n", result);
    }

    private static Long createOrMask(String mask) {
        long multiplier = 1L;
        long resultMask = 0L;
        for (int i = mask.length() - 1; i >= 0; i--) {
            if (mask.charAt(i) == '1') {
                resultMask += multiplier;
            }
            multiplier *= 2;
        }
        return resultMask;
    }

    private static Long createAndMask(String mask) {
        long multiplier = 1L;
        long resultMask = 0L;
        for (int i = mask.length() - 1; i >= 0; i--) {
            if (mask.charAt(i) == '1' || mask.charAt(i) == 'X') {
                resultMask += multiplier;
            }
            multiplier *= 2;
        }
        return resultMask;
    }

    private static List<Long> getEveryAffectedRegister(String mask, Long initialValue) {
        long multiplier = 1L;
        List<Long> registers = new ArrayList<>();
        registers.add(initialValue);
        for (int i = mask.length() - 1; i >= 0; i--) {
            List<Long> nextIterationOfRegisters = new ArrayList<>();
            if (mask.charAt(i) == '1') {
                for (Long entry : registers) {
                    nextIterationOfRegisters.add(entry | multiplier);
                    registers = nextIterationOfRegisters;
                }
            } else if (mask.charAt(i) == 'X') {
                for (Long entry : registers) {
                    nextIterationOfRegisters.add(entry | multiplier);
                    nextIterationOfRegisters.add(entry & ~multiplier);
                    registers = nextIterationOfRegisters;
                }
            }
            multiplier *= 2;
        }
        return registers;
    }

}