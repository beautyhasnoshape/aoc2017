package beauty.aoc2017;

import java.util.List;

/**

 */
public class Day19 {

    private enum Move {
        n (-1, 0),
        s (1, 0),
        w (0, -1),
        e (0, 1);

        int dx;
        int dy;

        Move(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int getDx() {
            return dx;
        }

        public int getDy() {
            return dy;
        }

        public static Move getMove(int dx, int dy) {
            for (Move move : Move.values()) {
                if (move.dx == dx && move.dy == dy) {
                    return move;
                }
            }

            throw new IllegalArgumentException("Cannot find move for (dx, dy)=(" + dx + ", " + dy + ")");
        }
    }

    private class Cursor {
        private char[][] matrix;
        private int x;
        private int y;
        private Move move;

        private int steps = 1;

        public Cursor(char[][] matrix, int x, int y, Move move) {
            this.matrix = matrix;
            this.x = x;
            this.y = y;
            this.move = move;
        }

        public String move() {
            String result = "";

            x += move.getDx();
            y += move.getDy();

            char code = matrix[x][y];
            if (code == '|' || code == '-') {
                // continue
            } else if (code == '+') {
                // turn
                if (move.getDx() == 0) {
                    Move newMove = Move.getMove(-1, 0);
                    if (new Cursor(matrix, x, y, newMove).hasNext()) {
                        move = newMove;
                    } else {
                        move = Move.getMove(1, 0);
                    }
                } else {
                    Move newMove = Move.getMove(0, -1);
                    if (new Cursor(matrix, x, y, newMove).hasNext()) {
                        move = newMove;
                    } else {
                        move = Move.getMove(0, 1);
                    }
                }
            } else if (code >= 'A' && code <= 'Z') {
                // continue
                result = String.valueOf(code);
            } else if (code == ' ') {
                // stop
                result = null;
            } else {
                throw new IllegalArgumentException("Unknown code: " + code);
            }

            ++steps;

            return result;
        }

        public boolean hasNext() {
            int newX = x + move.getDx();
            int newY = y + move.getDy();

            char code = matrix[newX][newY];

            return code != ' ';
        }

        public int getSteps() {
            return steps;
        }
    }

    public String solvePartA(List<String> lines) {
        String result = "";
        char[][] matrix = createMatrix(lines);

        int x = 0;
        int y = findStartY(matrix[x]);

        Cursor cursor = new Cursor(matrix, x, y, Move.s);
        while (cursor.hasNext()) {
            result += cursor.move();
        }

        return result;
    }

    public int solvePartB(List<String> lines) {
        char[][] matrix = createMatrix(lines);

        int x = 0;
        int y = findStartY(matrix[x]);

        Cursor cursor = new Cursor(matrix, x, y, Move.s);
        while (cursor.hasNext()) {
            cursor.move();
        }

        return cursor.getSteps();
    }

    private int findStartY(char[] array) {
        for (int i = 0; i < array.length; i++) {
            if ('|' == array[i]) {
                return i;
            }
        }

        throw new IllegalStateException("Cannot find starting column in the first row");
    }

    private char[][] createMatrix(List<String> lines) {
        int rows = lines.size();
        int cols = maxLength(lines);

        char[][] matrix = new char[rows][cols];

        for (int row = 0; row < rows; row++) {
            char[] line = lines.get(row).toCharArray();
            for (int col = 0; col < cols; col++) {
                char code = (col >= line.length ? ' ' : line[col]);
                matrix[row][col] = code;
            }
        }

        return matrix;
    }

    private int maxLength(List<String> lines) {
        int max = 0;

        for (String line : lines) {
            if (line.length() > max) {
                max = line.length();
            }
        }

        return max;
    }
}
