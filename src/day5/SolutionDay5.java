package day5;

import java.util.List;
import java.util.stream.Collectors;

import static utility.AOCUtility.readFile;

public class SolutionDay5 {
    public static void main(String[] args) {
        var fileName = "resources/input/day5.txt";
        List<String> input = readFile(fileName);

        List<Integer> result = input.stream().map(SolutionDay5::binaryConverter).collect(Collectors.toList());
        int solutionA = result.stream().mapToInt(v -> v).max().orElse(-1);

        System.out.println("SolutionA = " + solutionA);

        for (int i = 0; i < solutionA; i++) {
            if (!result.contains(i) && result.contains(i-1) && result.contains(i+1)){
                System.out.println("SolutionB = " + i);
            }
        }
    }

    private static int binaryConverter(String s) {
        var binary1 = s.chars().mapToObj(i->(char)i).map((c) -> {
            if (c.equals('B') || c.equals('R'))
                return "1";
            else
                return "0";
        }).collect(Collectors.joining());

        return Integer.parseInt(binary1, 2);
    }
}
