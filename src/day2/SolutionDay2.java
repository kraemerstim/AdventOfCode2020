package day2;

import static utility.AOCUtility.readFile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolutionDay2 {
	private static Pattern pattern = Pattern.compile("(\\d*)-(\\d*) (\\S): (\\S*)");

	public static void main(String[] args) {
		var fileName = "resources/input/day2.txt";
		List<String> input = readFile(fileName);

		long policyACorrectCount = input.stream().map(SolutionDay2::convertToPasswordPolicy).filter(SolutionDay2::patternMatchesPolicy1).count();
		long policyBCorrectCount = input.stream().map(SolutionDay2::convertToPasswordPolicy).filter(SolutionDay2::patternMatchesPolicy2).count();
		System.out.println("part1: " + policyACorrectCount);
		System.out.println("part2: " + policyBCorrectCount);
	}

	private static PasswordPolicy convertToPasswordPolicy(String policy) {
		Matcher matcher = pattern.matcher(policy);
		if (matcher.matches()) {
			int int1 = Integer.parseInt(matcher.group(1));
			int int2 = Integer.parseInt(matcher.group(2));
			char character = matcher.group(3).toCharArray()[0];
			String password = matcher.group(4);
			return new PasswordPolicy(int1, int2, character, password);
		} else {
			throw new IllegalArgumentException("pattern does not match a policy");
		}
	}

	private static boolean patternMatchesPolicy1(PasswordPolicy policy) {
		int counter = 0;
		for (char s : policy.password.toCharArray()) {
			if (s == policy.character) {
				counter++;
			}
		}
		return (counter >= policy.int1 && counter <= policy.int2);
	}

	private static boolean patternMatchesPolicy2(PasswordPolicy policy) {
		var array = policy.password.toCharArray();
		return (array[policy.int1 - 1] == policy.character) ^ (array[policy.int2 - 1] == policy.character);
	}


	static class PasswordPolicy {
		int int1;
		int int2;
		char character;
		String password;

		PasswordPolicy(int int1, int int2, char character, String password) {
			this.int1 = int1;
			this.int2 = int2;
			this.character = character;
			this.password = password;
		}
	}
}
