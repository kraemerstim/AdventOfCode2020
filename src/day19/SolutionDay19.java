package day19;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utility.AOCUtility.readFile;

public class SolutionDay19 {
    public static void main(String[] args) {
        final String PATTERN_RULE = "^(\\d+): (.+)";
        final String PATTERN_RULE_CHARACTER = "^\"(\\w)\"$";
        Pattern rulesPattern = Pattern.compile(PATTERN_RULE);
        Pattern ruleCharacterPattern = Pattern.compile(PATTERN_RULE_CHARACTER);

        var fileName_rules = "resources/input/day19_rules.txt";
        List<String> rules = readFile(fileName_rules);

        Map<Integer, Rule> ruleMap = new HashMap<>();

        for (String rule: rules) {
            Matcher ruleMatcher = rulesPattern.matcher(rule);
            if (ruleMatcher.matches()) {
                int idPart = Integer.parseInt(ruleMatcher.group(1));
                String secondPart = ruleMatcher.group(2);

                Rule newRule = ruleMap.getOrDefault(idPart, new Rule(idPart));
                ruleMap.putIfAbsent(idPart, newRule);

                Matcher characterMatcher = ruleCharacterPattern.matcher(secondPart);
                if (characterMatcher.matches()) {
                    newRule.addCharacter(characterMatcher.group(1));
                } else
                {
                    for (String ruleGroup: secondPart.split(" \\| ")) {
                        List<Rule> tempRuleList = new ArrayList<>();
                        for (String depend: ruleGroup.split(" ")){
                            int ruleID = Integer.parseInt(depend);
                            Rule tempRule = ruleMap.getOrDefault(ruleID, new Rule(ruleID));
                            ruleMap.putIfAbsent(ruleID, tempRule);
                            tempRuleList.add(tempRule);
                        }
                        newRule.addNewDependingRules(tempRuleList);
                    }
                }
            }
        }

        Rule rule0 = ruleMap.get(0);
        Rule rule42 = ruleMap.get(42);
        Rule rule31 = ruleMap.get(31);

        Set<String> rule0AcceptedStrings = rule0.getAllAcceptedStrings();

        var fileName_input = "resources/input/day19_input.txt";
        List<String> input = readFile(fileName_input);

        int count = 0;
        for (String pattern: input){
            if (rule0AcceptedStrings.contains(pattern)) count++;
        }

        System.out.format("SolutionA= %d\n", count);

        // Known things about the second task:
        // 1. its 42+ 42*x 31*x
        // 2. 42 and 31 are always 8 characters long
        // -> input length has to be dividable by 8 and at least 24 characters long
        // -> check as much rule42 as possible and then continue with 31
        count = 0;
        for (String pattern: input){
            if (pattern.length() % 8 != 0 || pattern.length() < 20) continue;
            int counter42 = 0;
            int counter31 = 0;
            while (!pattern.isEmpty() && rule42.acceptedStrings.contains(pattern.substring(0,8))) {
                counter42++;
                pattern = pattern.substring(8);
            }
            while (!pattern.isEmpty() && rule31.acceptedStrings.contains(pattern.substring(0,8))) {
                counter31++;
                pattern = pattern.substring(8);
            }
            if (pattern.length() == 0 && counter31 >= 1 && counter42 > counter31) {
                count++;
            }
        }

        System.out.format("SolutionB= %d\n", count);

    }

    static class Rule {
        String character;
        int rule_id;
        List<List<Rule>> dependingRules;
        Set<String> acceptedStrings;

        Rule(int rule_id) {
            this.rule_id = rule_id;
            dependingRules = new ArrayList<>();
            acceptedStrings = new HashSet<>();
        }

        void addNewDependingRules(List<Rule> rules) {
            dependingRules.add(rules);
        }

        void addCharacter(String ruleCharacter) {
            character = ruleCharacter;
            acceptedStrings.add(character);
        }


        Set<String> getAllAcceptedStrings() {
            if (acceptedStrings.isEmpty())
            {
                for (var ruleSet: dependingRules) {
                    if (ruleSet.size() == 1) {
                        acceptedStrings.addAll(ruleSet.get(0).getAllAcceptedStrings());
                    } else {
                        Set<String> rules1 = ruleSet.get(0).getAllAcceptedStrings();
                        Set<String> rules2 = ruleSet.get(1).getAllAcceptedStrings();
                        for (String s1: rules1) {
                            for (String s2: rules2) {
                                acceptedStrings.add(s1+s2);
                            }
                        }
                    }
                }
            }
            return acceptedStrings;
        }
    }
}
