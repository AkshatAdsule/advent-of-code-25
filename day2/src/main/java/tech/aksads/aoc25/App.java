package tech.aksads.aoc25;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] ranges = input.split(",");

        long resultPart1 = 0;
        long resultPart2 = 0;

        for (String range : ranges) {
            String[] nums = range.split("-");
            long starting = Long.parseLong(nums[0]);
            long ending = Long.parseLong(nums[1]);

            for (long num = starting; num <= ending; ++num) {
                if (isInvalidPart1(num)) {
                    resultPart1 += num;
                }

                if (isInvalidPart2(num)) {
                    resultPart2 += num;
                }
            }
        }

        System.out.println("[Part1]: Sum of invalid IDs: " + resultPart1);
        System.out.println("[Part2]: Sum of invalid IDs: " + resultPart2);
        scanner.close();
    }

    static boolean isInvalidPart1(long num) {
        String numString = Long.toString(num);
        int numDigits = numString.length();

        if (numDigits % 2 != 0) {
            return false;
        }

        String firstHalf = numString.substring(0, numDigits / 2);
        String secondHalf = numString.substring(numDigits / 2);

        if (firstHalf.equals(secondHalf)) {
            return true;
        }
        return false;
    }

    static boolean isInvalidPart2(long num) {
        String numString = Long.toString(num);
        int numDigits = numString.length();

        for (int patternLength = 1; patternLength <= numDigits / 2; ++patternLength) {
            if (numDigits % patternLength != 0) {
                continue;
            }

            String pattern = numString.substring(0, patternLength);
            boolean foundPattern = true;
            for (int i = patternLength; i < numDigits && foundPattern; i += patternLength) {
                if (!pattern.equals(numString.substring(i, i + patternLength))) {
                    foundPattern = false;
                }
            }

            if (foundPattern) {
                return true;
            }
        }

        return false;
    }
}
