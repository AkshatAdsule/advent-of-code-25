package tech.aksads.aoc25;

public class Interval {
    long low;
    long high;

    public static Interval fromString(String intervalString) {
        String[] numStrings = intervalString.split("-");
        long low = Long.parseLong(numStrings[0]);
        long high = Long.parseLong(numStrings[1]);
        return new Interval(low, high);
    }

    public Interval(long low, long high) {
        this.low = low;
        this.high = high;
    }

    boolean overlapsOrAdjacent(Interval other) {
        return low <= other.high + 1 && other.low <= high + 1;
    }

    public boolean contains(long num) {
        return (num >= low) && (num <= high);
    }

    public long getNumValues() {
        return (high - low + 1);
    }

    @Override
    public String toString() {
        return low + "-" + high;
    }

}