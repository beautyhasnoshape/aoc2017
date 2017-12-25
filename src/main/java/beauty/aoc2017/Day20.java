package beauty.aoc2017;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**

 */
public class Day20 {

    private class Cube {
        private long x, y, z;

        public Cube(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o instanceof CubeWrapper) {
                return this.equals(((CubeWrapper) o).cube);
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Cube cube = (Cube) o;

            return x == cube.x && y == cube.y && z == cube.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "<" + x + ", " + y + ", " + z + '>';
        }
    }

    private class CubeWrapper {
        private int idx;
        private Cube cube;

        public CubeWrapper(Cube cube, int idx) {
            this.cube = cube;
            this.idx = idx;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o instanceof Cube) {
                return cube.equals(o);
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return cube.hashCode();
        }
    }

    public int solvePartB(List<String> lines) {
        List<Cube[]> particles = new ArrayList<>(lines.size());
        for (String line : lines) {
            // p, v, a
            Cube[] cubes = parseCubes(line);
            particles.add(cubes);
        }

        List<CubeWrapper> collideList = new ArrayList<>(particles.size());
        List<CubeWrapper> currentList = new ArrayList<>(particles.size());

        int repeats = 1000;

        for (int i = 0; i < repeats; i++) {
            // cube order in a particle: p, v, a
            for (int partNo = 0; partNo < particles.size(); partNo++) {
                Cube[] particle = particles.get(partNo);

                // udpate v = v + a
                particle[1] = increase(particle[1], particle[2]);

                // udpate p = p + v
                particle[0] = increase(particle[0], particle[1]);

                if (collideList.contains(particle[0])) {
                    collideList.add(new CubeWrapper(particle[0], partNo));
                } else if (currentList.contains(particle[0])) {
                    collideList.add(new CubeWrapper(particle[0], partNo));
                    collideList.add(new CubeWrapper(particle[0], currentList.indexOf(particle[0])));
                } else {
                    currentList.add(new CubeWrapper(particle[0], partNo));
                }
            }

            Iterator<Cube[]> particlesIter = particles.iterator();
            while (particlesIter.hasNext()) {
                Cube[] particle = particlesIter.next();
                if (collideList.contains(particle[0])) {
                    particlesIter.remove();
                }
            }

            currentList.clear();
            collideList.clear();
        }

        return particles.size();
    }

    public int solvePartA(List<String> lines) {
        List<Cube[]> particles = new ArrayList<>(lines.size());
        for (String line : lines) {
            // p, v, a
            Cube[] cubes = parseCubes(line);
            particles.add(cubes);
        }

        int partNoMin = -1;
        int repeats = 1000;

        for (int i = 0; i < repeats; i++) {
            long minDistance = Integer.MAX_VALUE;

            // cube order in a particle: p, v, a
            for (int partNo = 0; partNo < particles.size(); partNo++) {
                Cube[] particle = particles.get(partNo);

                // udpate v = v + a
                particle[1] = increase(particle[1], particle[2]);

                // udpate p = p + v
                particle[0] = increase(particle[0], particle[1]);

                if (i == repeats - 1) {
                    long distance = distance(particle[0], new Cube(0, 0, 0));
                    if (distance < minDistance) {
                        minDistance = distance;
                        partNoMin = partNo;
                    }
                }
            }
        }

        return partNoMin;
    }

    private long distance(Cube a, Cube b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y) + Math.abs(a.z - b.z);
    }

    private Cube increase(Cube cube, Cube diff) {
        return new Cube(cube.x + diff.x, cube.y + diff.y, cube.z + diff.z);
    }

    private Cube[] parseCubes(String line) {
        String[] groups = line.split(", ");

        return new Cube[] {
                parse(groups[0]),
                parse(groups[1]),
                parse(groups[2]),
        };
    }

    private Cube parse(String line) {
        String[] split = line.split("=");
        String id = split[0];
        split = split[1].substring(1, split[1].length() - 1).split(",");

        return new Cube(Integer.parseInt(split[0].trim()),
                        Integer.parseInt(split[1].trim()),
                        Integer.parseInt(split[2].trim()));
    }


}
