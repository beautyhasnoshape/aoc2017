package beauty.aoc2017;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Day16Test {

    private Day16 testObj;

    @Before
    public void setUp() throws Exception {
        testObj = new Day16();
    }

    @Test
    public void shouldSolvePartA() throws IOException, URISyntaxException {
        // given
        Path path = Paths.get(getClass().getResource("/Day16.txt").toURI());
        String[] elements = Files.readAllLines(path).get(0).split(",");

        List<Day16.Move<char[]>> moves = new ArrayList<>(elements.length);
        for (String element : elements) {
            moves.add(Day16.parse(element));
        }

        char[] programs = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p' };

        // when
        char[] result = testObj.solvePartA(moves, programs);

        // then
        assertEquals("ebjpfdgmihonackl", new String(result));
    }

    @Test
    public void shouldSolvePartB() throws IOException, URISyntaxException {
        // given
        Path path = Paths.get(getClass().getResource("/Day16.txt").toURI());
        String[] elements = Files.readAllLines(path).get(0).split(",");

        List<Day16.Move<char[]>> moves = new ArrayList<>(elements.length);
        for (String element : elements) {
            moves.add(Day16.parse(element));
        }

        char[] programs = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p' };

        // when
        char[] result = testObj.solvePartB(moves, programs);

        // then
        assertEquals("abocefghijklmndp", new String(result));
    }

    @Test
    public void shouldSolvePartASample() throws IOException {
        // given
        char[] programs = new char[] { 'a', 'b', 'c', 'd', 'e' };

        String[] elements = new String[] { "s1", "x3/4", "pe/b" };
        List<Day16.Move<char[]>> moves = new ArrayList<>(elements.length);
        for (String element : elements) {
            moves.add(Day16.parse(element));
        }

        // when
        char[] result = testObj.solvePartA(moves, programs);

        // then
        assertEquals("baedc", new String(result));
    }


    @Test
    public void shouldSolvePartBSample() throws IOException {
        // given
        char[] programs = new char[] { 'a', 'b', 'c', 'd', 'e' };

        String[] elements = new String[] { "s1", "x3/4", "pe/b" };
        List<Day16.Move<char[]>> moves = new ArrayList<>(elements.length);
        for (String element : elements) {
            moves.add(Day16.parse(element));
        }

        // when
        char[] result = testObj.solvePartB(moves, programs);

        // then
        assertEquals("abcde", new String(result));
    }
}