package beauty.aoc2017;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 --- Day 16: Permutation Promenade ---

 You come upon a very unusual sight; a group of programs here appear to be dancing.

 There are sixteen programs in total, named a through p. They start by standing in a line: a stands in position 0,
 b stands in position 1, and so on until p, which stands in position 15.

 The programs' dance consists of a sequence of dance moves:

 Spin, written sX, makes X programs move from the end to the front, but maintain their order otherwise.
 (For example, s3 on abcde produces cdeab).
 Exchange, written xA/B, makes the programs at positions A and B swap places.
 Partner, written pA/B, makes the programs named A and B swap places.
 For example, with only five programs standing in a line (abcde), they could do the following dance:

 s1, a spin of size 1: eabcd.
 x3/4, swapping the last two programs: eabdc.
 pe/b, swapping programs e and b: baedc.
 After finishing their dance, the programs end up in order baedc.

 You watch the dance for a while and record their dance moves (your puzzle input).
 In what order are the programs standing after their dance?

 Your puzzle answer was ebjpfdgmihonackl.

 --- Part Two ---

 Now that you're starting to get a feel for the dance moves, you turn your attention to the dance as a whole.

 Keeping the positions they ended up in from their previous dance, the programs perform it again and again:
 including the first dance, a total of one billion (1000000000) times.

 In the example above, their second dance would begin with the order baedc, and use the same dance moves:
 s1, a spin of size 1: cbaed.
 x3/4, swapping the last two programs: cbade.
 pe/b, swapping programs e and b: ceadb.

 In what order are the programs standing after their billion dances?
 */
public class Day16 {
    public char[] solvePartA(List<Move<char[]>> moves, char[] programs) {
        for (Move<char[]> move : moves) {
            programs = move.move(programs);
        }

        return programs;
    }

    /**
     * Too expensive to perform a brute force.
     * Permutation cannot be calculated and applied several times, because 'Partner' has no fixed positions.
     *
     * Idea: as seen in the sample case, find a cycle in permutations and then find the result within them.
     */
    public char[] solvePartB(List<Move<char[]>> moves, char[] programs) {
        List<String> processed = new LinkedList<>();
        String original = new String(programs);

        for (int i = 0; i < 1000000000; i++) {
            processed.add(new String(programs));

            programs = solvePartA(moves, programs);
            String value = new String(programs);
            if (value.equals(original)) {
                break;
            }
        }

        int idx = (1000000000 % processed.size()) < 0 ? processed.size() : 1000000000 % processed.size();
        String result = processed.get(idx);

        return result.toCharArray();
    }

    public static Move parse(String move) {
        char moveCode = move.charAt(0);
        Move result;
        if ('s' == moveCode) {
            byte len = Byte.parseByte(move.substring(1));
            result = new Spin(len);
        } else if ('x' == moveCode) {
            String[] values = move.substring(1).split("/");
            byte idxA = Byte.parseByte(values[0]);
            byte idxB = Byte.parseByte(values[1]);
            result = new Exchange(idxA, idxB);
        } else if ('p' == moveCode) {
            result = new Partner(move.charAt(1), move.charAt(3));
        } else {
            throw new IllegalArgumentException("Illegal dance move found '" + moveCode + "' in " + move);
        }

        return result;
    }

    public static class Spin implements Move<char[]> {
        private byte length;

        public Spin(byte length) {
            this.length = length;
        }

        @Override
        public char[] move(char[] programs) {
            return ArrayUtils.addAll(Arrays.copyOfRange(programs, programs.length - length, programs.length),
                    Arrays.copyOfRange(programs, 0, programs.length - length));
        }
    }

    public static class Exchange implements Move<char[]> {
        private byte indexA;
        private byte indexB;

        public Exchange(byte indexA, byte indexB) {
            this.indexA = indexA;
            this.indexB = indexB;
        }

        @Override
        public char[] move(char[] programs) {
            char remember = programs[indexA];
            programs[indexA] = programs[indexB];
            programs[indexB] = remember;

            return programs;
        }
    }

    public static class Partner implements Move<char[]> {
        private char a;
        private char b;

        public Partner(char a, char b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public char[] move(char[] programs) {
            byte idxA = -1;
            byte idxB = -1;

            for (byte idx = 0; idx < programs.length; idx++) {
                if (programs[idx] == a) {
                    idxA = idx;
                } else if (programs[idx] == b) {
                    idxB = idx;
                }

                if (idxA > -1 && idxB > -1) {
                    break;
                }
            }

            char remember = programs[idxA];
            programs[idxA] = programs[idxB];
            programs[idxB] = remember;

            return programs;
        }
    }

    public interface Move<T> {
        T move(T programs);
    }
}
