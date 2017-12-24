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

import static org.junit.Assert.*;

public class Day18Test {

    private Day18 testObj = new Day18();

    @Before
    public void setUp() throws Exception {
        testObj = new Day18();
    }

    @Test
    public void solvePartA() throws URISyntaxException, IOException {
        // given
        Path path = Paths.get(getClass().getResource("/Day18.txt").toURI());
        List<String> elements = Files.readAllLines(path);

        // when
        long freq = testObj.solvePartA(elements);

        // then
        assertEquals(1187L, freq);
    }

    @Test
    public void solvePartB() throws URISyntaxException, IOException {
        // given
        Path path = Paths.get(getClass().getResource("/Day18.txt").toURI());
        List<String> elements = Files.readAllLines(path);

        // when
        long freq = testObj.solvePartB(elements);

        // then
        assertEquals(5969L, freq);
    }

    @Test
    public void solvePartASample() {
        List<String> lines = Arrays.asList(new String[] {
                "set a 1",
                "add a 2",
                "mul a a",
                "mod a 5",
                "snd a",
                "set a 0",
                "rcv a",
                "jgz a -1",
                "set a 1",
                "jgz a -2"
        });

        long frequency = testObj.solvePartA(lines);

        assertEquals(4L, frequency);
    }
}