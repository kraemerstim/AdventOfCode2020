package day6;

import java.util.*;
import java.util.stream.Collectors;

import static utility.AOCUtility.readFile;

public class Solution6 {
    private static final boolean USE_FANCY_SOLUTION = true;

    public static void main(String[] args) {
        var fileName = "resources/input/day6.txt";
        List<String> input = readFile(fileName);

        if (USE_FANCY_SOLUTION)
            useFancySolution(input);
        else
            useReadableSolution(input);
    }

    private static void useFancySolution(List<String> input) {
        List<List<Set<Character>>> answers = new ArrayList<>();

        List<Set<Character>> currentGroup = new ArrayList<>();
        for (String line : input) {
            if (line.isEmpty()) {
                answers.add(currentGroup);
                currentGroup = new ArrayList<>();
            } else {
                currentGroup.add(line.chars().mapToObj(c -> (char)c).collect(Collectors.toSet()));
            }
        }
        answers.add(currentGroup);


        System.out.println("solutionA = " + answers.stream().mapToInt((group) -> group.stream().reduce(Solution6::combineTwoSets).get().size()).sum());
        System.out.println("solutionB = " + answers.stream().mapToInt((group) -> group.stream().reduce(Solution6::intersectTwoSets).get().size()).sum());
    }

    private static void useReadableSolution(List<String> input) {
        System.out.println("solutionA = " + getSumOfUnionGroupAnswers(input));
        System.out.println("solutionB = " + getSumOfIntersectGroupAnswers(input));
    }

    private static int getSumOfUnionGroupAnswers(List<String> input) {
        List<Set<Character>> groupsAny = new ArrayList<>();

        Set<Character> currentGroup = new HashSet<>();
        for (String line : input) {
            if (line.isEmpty()) {
                groupsAny.add(currentGroup);
                currentGroup = new HashSet<>();
            } else {
                currentGroup.addAll(line.chars().mapToObj(c -> (char)c).collect(Collectors.toSet()));
            }
        }
        groupsAny.add(currentGroup);
        return groupsAny.stream().mapToInt(Set::size).sum();
    }

    private static int getSumOfIntersectGroupAnswers(List<String> input) {
        List<Set<Character>> groupsAll = new ArrayList<>();

        boolean firstLine = true;
        Set<Character> currentGroup = new HashSet<>();
        for (String line : input) {
            if (line.isEmpty()) {
                groupsAll.add(currentGroup);
                currentGroup = new HashSet<>();
                firstLine = true;
            } else {
                Set<Character> answers = line.chars().mapToObj(c->(char)c).collect(Collectors.toSet());
                if (firstLine) {
                    currentGroup = answers;
                    firstLine = false;
                } else {
                    currentGroup.retainAll(answers);
                }
            }
        }
        groupsAll.add(currentGroup);
        return groupsAll.stream().mapToInt(Set::size).sum();
    }



    private static Set<Character> intersectTwoSets(Set<Character> set1, Set<Character> set2) {
        Set<Character> resultSet = new HashSet<>(set1);
        resultSet.retainAll(set2);
        return resultSet;
    }

    private static Set<Character> combineTwoSets(Set<Character> set1, Set<Character> set2) {
        Set<Character> resultSet = new HashSet<>(set1);
        resultSet.addAll(set2);
        return resultSet;
    }

}
