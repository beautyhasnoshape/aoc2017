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

public class Day19Test {

    private Day19 testObj;

    @Before
    public void setUp() {
        testObj = new Day19();
    }

    @Test
    public void solvePartA() throws URISyntaxException, IOException {
        // given
        Path path = Paths.get(getClass().getResource("/Day19.txt").toURI());
        List<String> elements = Files.readAllLines(path);

        // when
        String text = testObj.solvePartA(elements);

        // then
        assertEquals("XYFDJNRCQA", text);
    }

    @Test
    public void solvePartB() throws URISyntaxException, IOException {
        // given
        Path path = Paths.get(getClass().getResource("/Day19.txt").toURI());
        List<String> elements = Files.readAllLines(path);

        // when
        int steps = testObj.solvePartB(elements);

        // then
        assertEquals(17450, steps);
    }

    @Test
    public void solvePartASample() {
        List<String> lines = Arrays.asList(new String[] {
                        "     |          ",
                        "     |  +--+    ",
                        "     A  |  C    ",
                        " F---|----E|--+ ",
                        "     |  |  |  D ",
                        "     +B-+  +--+ "});

        String text = testObj.solvePartA(lines);

        assertEquals("ABCDEF", text);
    }

    @Test
    public void solvePartBSample() {
        List<String> lines = Arrays.asList(new String[] {
                "     |          ",
                "     |  +--+    ",
                "     A  |  C    ",
                " F---|----E|--+ ",
                "     |  |  |  D ",
                "     +B-+  +--+ "});

        int steps = testObj.solvePartB(lines);

        assertEquals(38, steps);
    }
}