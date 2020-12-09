package day4;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utility.AOCUtility.readFile;

public class SolutionDay4 {
    private static String[] valid_keys = {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};

    public static void main(String[] args) {
        var fileName = "resources/input/day4.txt";
        List<String> input = readFile(fileName);

        List<Map<String, String>> passports = new ArrayList<>();

        Map<String, String> currentPassport = new HashMap<>();
        for (String line : input) {
            if (line.isEmpty()) {
                passports.add(currentPassport);
                currentPassport = new HashMap<>();
            } else {
                String[] items = line.split("\\s* \\s*");
                for (String item : items) {
                    String[] mapParts = item.split("\\s*:\\s*");
                    currentPassport.put(mapParts[0], mapParts[1]);
                }
            }
        }
        passports.add(currentPassport);

        long solutionA = passports.stream().filter((p) -> checkPassport(p, false)).count();
        long solutionB = passports.stream().filter((p) -> checkPassport(p, true)).count();

        System.out.println("Solution for partA = " + solutionA);
        System.out.println("Solution for partB = " + solutionB);

    }

    private static boolean checkPassport(Map<String, String> passport, boolean checkValidity) {
        for (String key : valid_keys) {
            if (!passport.containsKey(key) ||
                    (checkValidity && !checkValidityOfField(key, passport.get(key)))) return false;
        }
        return true;
    }

    private static boolean isYear(String year) {
        Pattern pattern = Pattern.compile("^[0-9]{4}$");
        Matcher matcher = pattern.matcher(year);
        return matcher.matches();
    }

    private static boolean checkValidityOfField(String key, String value) {
        int year;
        int height;
        Pattern pattern;
        Matcher matcher;
        switch (key) {
            case "byr":
                if (isYear(value)) return false;
                year = Integer.parseInt(value);
                if (year > 2002 || year < 1920) return false;
                break;
            case "iyr":
                if (isYear(value)) return false;
                year = Integer.parseInt(value);
                if (year > 2020 || year < 2010) return false;
                break;
            case "eyr":
                if (isYear(value)) return false;
                year = Integer.parseInt(value);
                if (year > 2030 || year < 2020) return false;
                break;
            case "hgt":
                pattern = Pattern.compile("^(\\d*)(in|cm)$");
                matcher = pattern.matcher(value);
                if (!matcher.matches()) return false;
                if (matcher.group(2).equals("in")) {
                    height = Integer.parseInt(matcher.group(1));
                    if (height > 76 || height < 59) return false;
                } else if (matcher.group(2).equals("cm")) {
                    height = Integer.parseInt(matcher.group(1));
                    if (height > 193 || height < 150) return false;
                } else return false;
                break;
            case "hcl":
                pattern = Pattern.compile("^#[0-9a-f]{6}$");
                matcher = pattern.matcher(value);
                if (!matcher.matches()) return false;
                break;
            case "ecl":
                if (!List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value)) return false;
                break;
            case "pid":
                pattern = Pattern.compile("^[0-9]{9}$");
                matcher = pattern.matcher(value);
                if (!matcher.matches()) return false;
                break;
            default:
                break;
        }

        return true;
    }
}
