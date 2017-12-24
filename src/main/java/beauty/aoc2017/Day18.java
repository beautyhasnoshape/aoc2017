package beauty.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

 */
public class Day18 {

    public int solvePartB(List<String> instructions) {
        List<Long> q0 = new ArrayList<>();
        List<Long> q1 = new ArrayList<>();
        Program p0 = new Program(instructions, 0, q0, q1);
        Program p1 = new Program(instructions, 1, q1, q0);

        try {
            Thread t0 = new Thread(p0, "Program 0");
            Thread t1 = new Thread(p1, "Program 1");

            t0.start();
            t1.start();

            int sendCountP0 = 0;
            int sendCountP1 = 0;

            while (true) {
               if (p0.isStarving() && p1.isStarving()) {
                    if (sendCountP0 == p0.getSendCount() && sendCountP1 == p1.getSendCount()) {
                        t1.interrupt();
                        t0.interrupt();

                        break;
                    } else {
                        sendCountP0 = p0.getSendCount();
                        sendCountP1 = p1.getSendCount();
                    }
                }

                Thread.sleep(500);
            }
        } catch (IllegalThreadStateException | InterruptedException ex) {
            // OK
        }

        return p1.getSendCount();
    }

    private class Program implements Runnable {
        private long programId;
        private List<String> instructions;
        private List<Long> inQueue;
        private List<Long> outQueue;
        private boolean starving = false;

        private int sendCount = 0;
        private Map<String, Long> registers = new HashMap<>();

        private int currentPosition;

        public Program(List<String> instructions, long programId, List<Long> inQueue, List<Long> outQueue) {
            this.instructions = instructions;
            this.programId = programId;
            this.inQueue = inQueue;
            this.outQueue = outQueue;

            for (char ch = 'a'; ch < 'z'; ch++) {
                registers.put(String.valueOf(ch), 0L);
            }

            registers.put(String.valueOf('p'), programId);
        }

        @Override
        public void run() {
            currentPosition = 0;

            do {
                String line = instructions.get(currentPosition);
                String[] split = line.split(" ");
                String instruction = split[0];
                String x = split[1];
                String y = split.length < 3 ? null : split[2];
                Long valueX = resolveValue(x);
                Long valueY = y == null ? null : resolveValue(y);

                if ("set".equals(instruction)) {
                    registers.put(x, valueY);
                } else if ("add".equals(instruction)) {
                    registers.put(x, valueX + valueY);
                } else if ("mul".equals(instruction)) {
                    registers.put(x, valueX * valueY);
                } else if ("mod".equals(instruction)) {
                    registers.put(x, valueX % valueY);
                } else if ("snd".equals(instruction)) {
                    synchronized (outQueue) {
                        outQueue.add(valueX);
                        ++sendCount;
                    }
                } else if ("rcv".equals(instruction)) {
                    Long rcvValue = null;

                    do {
                        synchronized (inQueue) {
                            if (!inQueue.isEmpty()) {
                                rcvValue = inQueue.get(0);
                                inQueue.remove(0);
                                starving = false;
                            }
                        }

                        if (rcvValue == null) {
                            try {
                                starving = true;
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {
                                throw new IllegalThreadStateException(ex.getMessage());
                            }
                        }
                    } while (rcvValue == null);

                    registers.put(x, rcvValue);
                } else if ("jgz".equals(instruction)) {
                    if (valueX > 0) {
                        currentPosition += valueY;
                        continue;
                    }
                }

                ++currentPosition;
                // System.out.println(line + StringUtils.repeat(" ", 15 - line.length()) + x + ": " + valueX + " -> " + resolveValue(x));
            } while (currentPosition >=0 && currentPosition < instructions.size());
        }

        public int getSendCount() {
            return sendCount;
        }

        private long resolveValue(String argument) {
            return registers.containsKey(argument) ? registers.get(argument) : Long.parseLong(argument);
        }

        public boolean isStarving() {
            return starving;
        }

        @Override
        public String toString() {
            return "Program{" +
                    "programId=" + programId +
                    ", sendCount=" + sendCount +
                    ", currentPosition=" + currentPosition +
                    '}';
        }
    }
    private Map<String, Long> registers;

    private int currentPosition;

    public Day18() {
        registers = new HashMap<>();

        for (char ch = 'a'; ch < 'z'; ch++) {
            registers.put(String.valueOf(ch), 0L);
        }
    }

    private long resolveValue(String argument) {
        return registers.containsKey(argument) ? registers.get(argument) : Long.parseLong(argument);
    }

    /**
     * Operations performed on registers may not fit in integer, thus longs instead.
     */
    public long solvePartA(List<String> instructions) {
        currentPosition = 0;
        Long lastPlayedFrequency = null;

        do {
            String line = instructions.get(currentPosition);
            String[] split = line.split(" ");
            String instruction = split[0];
            String x = split[1];
            String y = split.length < 3 ? null : split[2];
            Long valueX = resolveValue(x);
            Long valueY = y == null ? null : resolveValue(y);

            if ("set".equals(instruction)) {
                registers.put(x, valueY);
            } else if ("add".equals(instruction)) {
                registers.put(x, valueX + valueY);
            } else if ("mul".equals(instruction)) {
                registers.put(x, valueX * valueY);
            } else if ("mod".equals(instruction)) {
                registers.put(x, valueX % valueY);
            } else if ("snd".equals(instruction)) {
                lastPlayedFrequency = valueX;
            } else if ("rcv".equals(instruction)) {
                if (valueX != 0) {
                    break;
                }
            } else if ("jgz".equals(instruction)) {
                if (valueX > 0) {
                    currentPosition += valueY;
                    continue;
                }
            }

            ++currentPosition;
            // System.out.println(line + StringUtils.repeat(" ", 15 - line.length()) + x + ": " + valueX + " -> " + resolveValue(x));
        } while (currentPosition >=0 && currentPosition < instructions.size());

        return lastPlayedFrequency;
    }


}
