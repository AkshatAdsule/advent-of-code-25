package tech.aksads.aoc25;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Intervals {
    private List<Interval> intervals;

    public Intervals() {
        intervals = new ArrayList<>();
    }

    public void addInterval(Interval interval) {
        // see if there is an existing interval we can extend
        Interval workingInterval = null;
        for (Interval existingInterval : intervals) {
            if (existingInterval.overlapsOrAdjacent(interval)) {
                if (workingInterval == null) {
                    workingInterval = existingInterval;
                }
                workingInterval.low = Math.min(workingInterval.low, Math.min(existingInterval.low, interval.low));
                workingInterval.high = Math.max(workingInterval.high, Math.max(existingInterval.high, interval.high));
                if (workingInterval != existingInterval) {
                    existingInterval.low = -1;
                    existingInterval.high = -1;
                }
            }
        }
        if (workingInterval == null) {
            this.intervals.add(interval);
        }
        intervals = intervals.stream().filter(i -> i.low != -1).collect(Collectors.toList());
    }

    public boolean contains(Long num) {
        for (Interval interval : intervals) {
            if (interval.contains(num)) {
                return true;
            }
        }
        return false;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }
}
