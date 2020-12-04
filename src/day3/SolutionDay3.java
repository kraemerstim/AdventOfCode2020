package day3;

import java.util.ArrayList;
import java.util.List;

import static utility.AOCUtility.readFile;

public class SolutionDay3 {

    static int[][] slopes = {{1,1},{3,1},{5,1},{7,1},{1,2}};

    public static void main(String[] args) {
        var fileName = "resources/input/day3.txt";
        List<String> input = readFile(fileName);

        List<Long> resultsForRuns = new ArrayList<>();

        for (int[] run: slopes) {
            Long treeCounter = 0L;
            for (int i = 0; i < input.size(); i = i + run[1]) {
                if (getTree(input.get(i), i/run[1], run[0])) treeCounter++;
            }
            resultsForRuns.add(treeCounter);
            System.out.println("Solution for Slope (Right " + run[0] + ", down " + run[1] + "): " + treeCounter);
        }
        long solutionb = resultsForRuns.stream().reduce(1L, (a,b) -> a*b);
        System.out.println("Solution for part 2: " + solutionb);
    }

    private static boolean getTree(String s, int line_Index, int move_right) {
        return s.toCharArray()[(line_Index * move_right) % s.length()] == '#';
    }
}
