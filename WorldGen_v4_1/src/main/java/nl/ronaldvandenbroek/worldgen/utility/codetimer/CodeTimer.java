package nl.ronaldvandenbroek.worldgen.utility.codetimer;

import nl.ronaldvandenbroek.worldgen.properties.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CodeTimer implements ICodeTimer {
    private static String ROUND_TIME = "Round Time ";
    private static String TOTAL_TIME = "Total Time ";
    private static String AVERAGE_ROUND_TIME = " with an average round time of ";
    private static String SPACE = " ";
    private static String DIVIDER = ": ";
    private static String UNIT = " millis";

    private String name;
    private long startTime;
    private long roundTime;
    private int roundNumber;

    private List<Long> roundTimes;

    public CodeTimer(String name) {
        roundTimes = new ArrayList<>();
        this.name = name;
        this.roundNumber = 0;
    }

    @Override
    public void start() {
        startTime = System.nanoTime();
        roundTime = startTime; // First round starts at the same time
    }

    @Override
    public void round() {
        long endRoundTime = System.nanoTime();
        long totalRoundTime = endRoundTime - roundTime;
        roundTimes.add(totalRoundTime);
        if (Config.ENABLE_ROUND_LOG) {
            System.out.println(ROUND_TIME + roundNumber + SPACE + name + DIVIDER + TimeUnit.NANOSECONDS.toMillis(totalRoundTime) + UNIT);
        }
        roundTime = endRoundTime;
        roundNumber += 1;
    }

    @Override
    public void end() {
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.print(TOTAL_TIME + name + DIVIDER + TimeUnit.NANOSECONDS.toMillis(totalTime) + UNIT);

        long averageTime = averageRoundTime();
        if (averageTime != 0) {
            System.out.println(AVERAGE_ROUND_TIME + TimeUnit.NANOSECONDS.toMillis(averageRoundTime()) + UNIT);
        } else {
            System.out.println("");
        }
    }

    @Override
    public long averageRoundTime() {
        int totalRounds = roundTimes.size();
        if (totalRounds == 0) {
            return 0;
        }
        long totalTime = 0;
        for (long roundTime : roundTimes) {
            totalTime += roundTime;
        }
        return totalTime / totalRounds;
    }
}
