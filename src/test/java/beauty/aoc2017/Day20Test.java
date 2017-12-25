package beauty.aoc2017;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day20Test {

    private Day20 testObj;

    @Before
    public void setUp() {
        testObj = new Day20();
    }

    @Test
    public void solvePartA() throws URISyntaxException, IOException {
        // given
        Path path = Paths.get(getClass().getResource("/Day20.txt").toURI());
        List<String> elements = Files.readAllLines(path);

        // when
        int partNo = testObj.solvePartA(elements);

        // then
        assertEquals(91, partNo);
    }

    @Test
    public void solvePartB() throws URISyntaxException, IOException {
        // given
        Path path = Paths.get(getClass().getResource("/Day20.txt").toURI());
        List<String> elements = Files.readAllLines(path);

        // when
        int partNo = testObj.solvePartB(elements);

        // then
        assertEquals(567, partNo);
    }

    @Test
    public void solvePartASample() {
        // given
        List<String> elements = Arrays.asList(new String[] {
                "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>",
                "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"
        });

        // when
        int partNo = testObj.solvePartA(elements);

        // then
        assertEquals(0, partNo);

    }

    @Test
    public void solvePartBSample() {
        List<String> elements = Arrays.asList(new String[] {
                "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>",
                "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"
        });

        // when
        int partNo = testObj.solvePartA(elements);

        // then
        assertEquals(-1, partNo);
    }
}