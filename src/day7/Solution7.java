package day7;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utility.AOCUtility.readFile;

public class Solution7 {
    private static final String COLOR_PATTERN = "^(.*) bags contain (.*)\\.$";
    private static final String CONTAINS_PATTERN = "(\\d*) (\\D*) bag[s]?";

    public static void main(String[] args) {
        var fileName = "resources/input/day7.txt";
        List<String> input = readFile(fileName);

        Map<String, HierarchyNode> colorList = new HashMap<>();
        Pattern colorPattern = Pattern.compile(COLOR_PATTERN);
        Pattern containsPattern = Pattern.compile(CONTAINS_PATTERN);
        Matcher colorMatcher;
        Matcher containsMatcher;

        for (String condition: input) {
            colorMatcher = colorPattern.matcher(condition);
            if (colorMatcher.matches()) {
                String color = colorMatcher.group(1);
                String contains = colorMatcher.group(2);

                HierarchyNode node = colorList.getOrDefault(color, new HierarchyNode(color));
                colorList.putIfAbsent(color, node);

                containsMatcher = containsPattern.matcher(contains);
                while(containsMatcher.find()) {
                String contains_color = containsMatcher.group(2);
                    HierarchyNode childNode = colorList.getOrDefault(contains_color, new HierarchyNode(contains_color));
                    colorList.putIfAbsent(contains_color, childNode);

                    if (!containsMatcher.group(2).equals("other")) { //no other bags inside
                        Integer containsCount = Integer.parseInt(containsMatcher.group(1));
                        node.addChild(childNode, containsCount);
                        childNode.addParent(node);
                    }
                }
            }
        }

        Set<HierarchyNode> resultSet = new HashSet<>();
        HierarchyNode shinyGoldBag = colorList.get("shiny gold");

        gatherParents(shinyGoldBag, resultSet);

        System.out.println(resultSet.size());

        System.out.println(shinyGoldBag.getNumberOfIncludedBags());
    }

    private static void gatherParents(HierarchyNode base, Set<HierarchyNode> resultSet) {
        for (HierarchyNode parent: base.parents) {
            resultSet.add(parent);
            gatherParents(parent, resultSet);
        }
    }

    static class HierarchyNode {
        Set<HierarchyNode> parents;
        Map<HierarchyNode, Integer> children;
        String color;

        HierarchyNode(String color) {
            parents = new HashSet<>();
            children = new HashMap<>();
            this.color = color;
        }

        void addParent(HierarchyNode parent) {
            parents.add(parent);
        }

        void addChild(HierarchyNode child, Integer count) {
            children.put(child, count);
        }

        long getNumberOfIncludedBags() {
            long includedBagsCount = 0L;
            for (Map.Entry<HierarchyNode, Integer> child: children.entrySet()) {
                int count = child.getValue();
                HierarchyNode bagType = child.getKey();

                includedBagsCount += count + count*bagType.getNumberOfIncludedBags();
            }
            return includedBagsCount;
        }
    }
}
