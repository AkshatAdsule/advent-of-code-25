package tech.aksads.aoc25;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Intervals intervals = new Intervals();
        List<Long> ids = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        boolean isReadingIntervals = true;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                isReadingIntervals = false;
                continue;
            }

            if (isReadingIntervals) {
                intervals.addInterval(Interval.fromString(line));
            } else {
                ids.add(Long.parseLong(line));
            }
        }
        scanner.close();

        System.out.println("Part 1: " + solvePart1(intervals, ids));
        System.out.println("Part 2: " + solvePart2(intervals));
    }

    public static long solvePart1(Intervals intervals, List<Long> ids) {
        return ids.stream().filter(intervals::contains).count();
    }

    public static long solvePart2(Intervals intervals) {
        return intervals.getIntervals().stream().mapToLong(Interval::getNumValues).sum();
    }
}
