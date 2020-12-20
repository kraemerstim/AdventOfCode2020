package day18;

import java.util.ArrayList;
import java.util.List;

import static utility.AOCUtility.readFile;

public class SolutionDay18 {
    public static void main(String[] args) {
        var fileName = "resources/input/day18.txt";
        List<String> input = readFile(fileName);

        long sum = 0;
        for (String line: input) {
            Expression expression = new Expression();
            sum += expression.parseExpression(line, false);
        }
        System.out.format("SolutionA = %d\n", sum);

        sum = 0;
        for (String line: input) {
            Expression expression = new Expression();
            sum += expression.parseExpression(line, true);
        }
        System.out.format("SolutionB = %d\n", sum);
    }

    static class Expression {
        long parseExpression(String expression, boolean additionBeforeMultiplication) {
            int startIndex = expression.indexOf('(');
            while(startIndex >= 0) {
                int endIndex = -1;
                int innerParentheses = 0;
                for (int i = startIndex+1; i < expression.length(); i++) {
                    if (expression.charAt(i) == '(') innerParentheses++;
                    if (expression.charAt(i) == ')') {
                        if (innerParentheses > 0) innerParentheses--;
                        else {
                            endIndex = i;
                            break;
                        }
                    }
                }
                Expression innerExpression = new Expression();
                long result = innerExpression.parseExpression(expression.substring(startIndex+1, endIndex), additionBeforeMultiplication);
                expression = expression.substring(0, startIndex) + result + expression.substring(endIndex+1);
                startIndex = expression.indexOf('(');
            }
            return parseStringsWithoutParentheses(expression, additionBeforeMultiplication);
        }

        private long parseStringsWithoutParentheses(String expression, boolean additionBeforeMultiplication) {
            List<String> expressionParts = new ArrayList<>(List.of(expression.split(" ")));
            if (additionBeforeMultiplication) {
                int plusIndex = expressionParts.indexOf("+");
                while (plusIndex >= 1) {
                    long tempValue = Long.parseLong(expressionParts.get(plusIndex - 1)) + Long.parseLong(expressionParts.get(plusIndex + 1));
                    expressionParts.set(plusIndex - 1, Long.toString(tempValue));
                    expressionParts.remove(plusIndex);
                    expressionParts.remove(plusIndex);
                    plusIndex = expressionParts.indexOf("+");
                }
            }
            char currentOperation = '_';
            long currentValue = 0;
            for (var part: expressionParts) {
                if (part.equals("+") || part.equals("*")) {
                    currentOperation = part.charAt(0);
                } else {
                    long value = Long.parseLong(part);
                    switch (currentOperation) {
                        case '_':
                            currentValue = value;
                            break;
                        case '*':
                            currentValue *= value;
                            break;
                        case '+':
                            currentValue += value;
                            break;
                    }
                }
            }
            return currentValue;
        }
    }
}
