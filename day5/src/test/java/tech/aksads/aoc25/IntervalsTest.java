package tech.aksads.aoc25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntervalsTest {
    private Intervals intervals;

    @BeforeEach
    public void setup() {
        intervals = new Intervals();
    }

    @Test
    public void shouldCheckSingleInterval() {
        Interval interval = new Interval(0, 5);
        intervals.addInterval(interval);

        assertTrue(intervals.contains(2l));
        assertFalse(intervals.contains(-1l));
    }

    @Test
    public void intervalSumTest() {
        Interval i = new Interval(3, 5);
        assertEquals(3, i.getNumValues());
    }
}
