package beauty.aoc2017;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day17Test {

    private Day17 testObj;

    @Before
    public void setUp() throws Exception {
        testObj = new Day17();
    }

    @Test
    public void solvePartA() {
        // given
        int step = 355;

        // when
        int result = testObj.solvePartA(step);

        // then
        assertEquals(1912, result);
    }

    @Test
    public void solvePartB() {
        // given
        int step = 355;

        // when
        int result = testObj.solvePartB(step);

        // then
        assertEquals(21066990, result);
    }
}