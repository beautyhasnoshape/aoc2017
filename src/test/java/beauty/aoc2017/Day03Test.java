package beauty.aoc2017;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Day03Test {
    private Day03 testObj;
    private int input;

    @Before
    public void setUp() throws Exception {
        testObj = new Day03();
        input = 277678;
    }

    @Test
    public void shouldSolveDiscretePartA() throws Exception {
        // given
        // when
        int result = testObj.solveDiscretePartA(input);

        // then
        assertEquals(475, result);
    }

    @Test
    public void shouldSolvePartA() throws Exception {
        // given
        // when
        int result = testObj.solvePartA(input);

        // then
        assertEquals(475, result);
    }
}