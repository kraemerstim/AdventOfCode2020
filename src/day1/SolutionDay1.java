package day1;

import java.util.List;
import java.util.stream.Collectors;

import static utility.AOCUtility.readFile;

public class SolutionDay1 {
    public static void main(String[] args) {
        var fileName = "resources/input/day1.txt";
        List<String> input = readFile(fileName);
        List<Integer> values = input.stream().map(Integer::parseInt).collect(Collectors.toList());

        boolean twoSolutionFound = false;
        boolean threeSolutionFound = false;
        for (int i: values) {
            for (int j: values) {
                for (int k: values) {
                    if (i + j == 2020 && !twoSolutionFound) {
                        System.out.println("1: " + i + " 2: " + j + " Ergebnis: " + (i * j));
                        twoSolutionFound = true;
                    }
                    if (i + j + k == 2020 && !threeSolutionFound) {
                        System.out.println("1: " + i + " 2: " + j + " 3: " + k +" Ergebnis: " + (i * j * k));
                        threeSolutionFound = true;
                    }
                }
            }
        }
    }

}
