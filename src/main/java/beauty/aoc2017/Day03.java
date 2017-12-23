package beauty.aoc2017;

/**
 --- Day 3: Spiral Memory ---

 You come across an experimental new kind of memory stored on an infinite two-dimensional grid.

 Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then counting up
 while spiraling outward. For example, the first few squares are allocated like this:

 17  16  15  14  13
 18   5   4   3  12
 19   6   1   2  11
 20   7   8   9  10
 21  22  23---> ...
 While this is very space-efficient (no squares are skipped), requested data must be carried back to square 1
 (the location of the only access port for this memory system) by programs that can only move up, down, left, or right.
 They always take the shortest path: the Manhattan Distance between the location of the data and square 1.

 For example:

 Data from square 1 is carried 0 steps, since it's at the access port.
 Data from square 12 is carried 3 steps, such as: down, left, left.
 Data from square 23 is carried only 2 steps: up twice.
 Data from square 1024 must be carried 31 steps.
 How many steps are required to carry the data from the square identified in your puzzle input all the way
 o the access port?

 Your puzzle answer was 475.

 --- Part Two ---

 As a stress test on the system, the programs here clear the grid and then store the value 1 in square 1.
 Then, in the same allocation order as shown above, they store the sum of the values in all adjacent squares,
 including diagonals.

 So, the first few squares' values are chosen as follows:

 Square 1 starts with the value 1.
 Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
 Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.
 Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
 Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.
 Once a square is written, its value does not change.
 Therefore, the first few squares would receive the following values:

 147  142  133  122   59
 304    5    4    2   57
 330   10    1    1   54
 351   11   23   25   26
 362  747  806--->   ...
 What is the first value written that is larger than your puzzle input?

 Your puzzle answer was 279138.

 Your puzzle input was 277678.
 */
public class Day03 {

    public int solveDiscretePartA(int input) {
        int degree = calculateSquareDegree(input);
        int normalizedInput = (input - (degree - 2) * (degree - 2)) % (degree - 1) + 1;
        int dA = Math.abs(normalizedInput - (degree + 1) / 2);
        int dB = Math.abs((degree - 1) / 2);

        return dA + dB;
    }

    private class Position implements Cloneable {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position move(Shift shift) {
            this.x += shift.getX();
            this.y += shift.getY();

            return this;
        }

        public int getX() {
            return x;
        }


        public int getY() {
            return y;
        }

        @Override
        protected Position clone() throws CloneNotSupportedException {
            return new Position(this.x, this.y);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private class Shift {
        private int x;
        private int y;

        public Shift(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Shift{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private Shift[] shifts = new Shift[] {
            new Shift(1, 0),
            new Shift(0, -1),
            new Shift(-1, 0),
            new Shift(0, 1),
    };

    private int[][] matrix;
    private int x;
    private int y;
    private int count;
    private int currentShift;
    private Position currentPosition;

    public Day03() {
    }

    public Integer solvePartA(int value) {
        // calculate size
        int degree = calculateSquareDegree(value);

        // create matrix
        matrix = new int[degree][degree];
        int xStart = (degree - 1) / 2;
        int yStart = xStart;

        count = 1;
        x = xStart;
        y = yStart;
        currentShift = 0;
        currentPosition = new Position(x, y);

        matrix[x][y] = count;

        for (int i = 1; i < value; i++) {
            move();
        }

        int distance = Math.abs(currentPosition.getX() - x) + Math.abs(currentPosition.getY() - y);

        return distance;
    }

    private void move() {
        // move
        Shift shift = shifts[currentShift];
        currentPosition = currentPosition.move(shift);

        // update matrix after move
        updateMatrix(matrix, currentPosition, ++count);

        // turn left if possible (shift direction)
        Position currentPositionCopy;
        try {
            currentPositionCopy = currentPosition.clone();
        } catch (CloneNotSupportedException ex) {
            throw new IllegalStateException(ex);
        }

        Shift nextShift = peekNextShift();
        Position nextPosition = currentPositionCopy.move(nextShift);
        int nextPositionValue = getValue(matrix, nextPosition);
        if (nextPositionValue < 1) {
            currentShift = (currentShift + 1) % 4;
        }
    }

    private void updateMatrix(int[][] matrix, Position position, int value) {
        matrix[position.getX()][position.getY()] = value;
    }

    private int calculateSquareDegree(long value) {
        int degree = (int) Math.ceil(Math.sqrt(value));

        if (degree % 2 == 0) {
            ++degree;
        }

        return degree;
    }

    private Shift peekNextShift() {
        return shifts[(currentShift + 1) % 4];
    }

    private int getValue(int[][] matrix, Position position) {
        return matrix[position.getX()][position.getY()];
    }
}
