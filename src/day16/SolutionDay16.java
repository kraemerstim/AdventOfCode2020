package day16;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utility.AOCUtility.readFile;

public class SolutionDay16 {
    public static void main(String[] args) {
        final String PATTERN_RULES = "^(\\D*): (\\d*)-(\\d*) or (\\d*)-(\\d*)$";
        Pattern maskPattern = Pattern.compile(PATTERN_RULES);

        var fileName_rules = "resources/input/day16_rules.txt";
        List<String> input_rules = readFile(fileName_rules);

        List<Range> rulesList = new ArrayList<>();
        for (String line : input_rules) {
            Matcher matcher = maskPattern.matcher(line);
            if (matcher.matches()) {
                rulesList.add(new Range(matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5))));
            }
        }

        var fileName_my_ticket = "resources/input/day16_myTicket.txt";
        List<String> input_my_ticket = readFile(fileName_my_ticket);
        List<Integer> myTicket = new ArrayList<>();
        for (String number : input_my_ticket.get(0).split(",")) {
            myTicket.add(Integer.parseInt(number));
        }

        var fileName_nearby = "resources/input/day16_nearby.txt";
        List<String> input_nearby = readFile(fileName_nearby);
        List<List<Integer>> nearbyList = new ArrayList<>();
        for (String line : input_nearby) {
            List<Integer> tempList = new ArrayList<>();
            for (String number : line.split(",")) {
                tempList.add(Integer.parseInt(number));
            }
            nearbyList.add(tempList);
        }

        int errorSum = 0;
        List<List<Integer>> validTickets = new ArrayList<>();
        for (List<Integer> passport : nearbyList) {
            boolean passportIsValid = true;
            for (Integer number : passport) {
                boolean isInRange = false;
                for (Range range : rulesList) {
                    if (range.isInRange(number)) {
                        isInRange = true;
                        break;
                    }
                }
                if (!isInRange) {
                    errorSum += number;
                    passportIsValid = false;
                }
            }
            if (passportIsValid) {
                validTickets.add(passport);
            }
        }

        System.out.format("SolutionA = %d\n", errorSum);

        for (Range range : rulesList) {
            for (int index = 0; index < rulesList.size(); index++) {
                boolean isPossibleIndex = true;
                for (List<Integer> passport : validTickets) {
                    if (!range.isInRange(passport.get(index))) {
                        isPossibleIndex = false;
                        break;
                    }
                }
                if (isPossibleIndex) {
                    range.possibleIndexes.add(index);
                }
            }
        }

        int rangesSet = 0;
        while (rangesSet < rulesList.size()) {
            for (Range range : rulesList) {
                if (range.index >= 0) continue;
                if (range.possibleIndexes.size() == 1) {
                    range.index = range.possibleIndexes.get(0);
                    removeFromRangelists(range.index, rulesList);
                    rangesSet += 1;
                }
            }
        }

        List<Integer> indexList = new ArrayList<>();
        for (Range range : rulesList) {
            if (range.name.startsWith("departure")) {
                indexList.add(range.index);
            }
        }

        long multiply_result = 1;
        for (Integer i : indexList) {
            multiply_result *= myTicket.get(i);
        }

        System.out.format("SolutionB = %d\n", multiply_result);
    }

    private static void removeFromRangelists(Integer index, List<Range> rangeList) {
        for (Range range : rangeList) {
            range.possibleIndexes.remove(index);
        }
    }


    static class Range {
        String name;
        int min1;
        int max1;
        int min2;
        int max2;
        int index;
        List<Integer> possibleIndexes;

        Range(String name, int min1, int max1, int min2, int max2) {
            this.name = name;
            this.min1 = min1;
            this.max1 = max1;
            this.min2 = min2;
            this.max2 = max2;
            possibleIndexes = new ArrayList<>();
            index = -1;
        }

        boolean isInRange(int i) {
            return (i >= min1 && i <= max1) || (i >= min2 && i <= max2);
        }
    }
}