import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File in = new File("input");
            Scanner reader = new Scanner(in);
            String input = reader.useDelimiter("\\A").next();
            solve(false, input);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void solve(boolean partOne, String input) {
        long answer = Long.MAX_VALUE;
        List<RangeMap> rangeMapList = new ArrayList<>();
        List<Range> rangeList = new ArrayList<>();
        String[] lines = input.split("\n");
        String[] stringSeeds = lines[0].split(" ");
        long[] seeds = new long[stringSeeds.length - 1];
        for (int i = 1; i < stringSeeds.length; i++) {
            seeds[i - 1] = Long.parseLong(stringSeeds[i]);
        }
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (line.isEmpty()) {
                continue;
            }
            if (line.contains("map")) {
                if (!rangeList.isEmpty()) {
                    rangeMapList.add(new RangeMap(rangeList));
                }
                rangeList = new ArrayList<>();
            } else {
                rangeList.add(new Range(line));
            }
        }
        rangeMapList.add(new RangeMap(rangeList));
        if (partOne) {
            for (long seed : seeds) {
                long val = seed;
                for (RangeMap map : rangeMapList) {
                    val = map.convert(val);
                }
                if (val < answer) {
                    answer = val;
                }
            }
        } else {
            for (int i = 0; i < seeds.length; i += 2) {
                for (long j = seeds[i]; j < seeds[i] + seeds[i + 1]; j++) {
                    long val = j;
                    long bound = 10000000000L;
                    for (RangeMap map : rangeMapList) {
                        bound = Math.min(bound, map.convert2(val)[1]);
                        val = map.convert2(val)[0];
                    }
                    long[] ret = {val, bound};
                    if (ret[0] < answer) {
                        answer = ret[0];
                    }
                    j += ret[1];
                }
            }
        }
        System.out.println(answer);
    }

    static class Range {
        long dst;
        long src;
        long range;

        public Range(long dst, long src, long r) {
            this.dst = dst;
            this.src = src;
            range = r;
        }

        public Range(String line) {
            String[] pieces = line.split(" ");
            dst = Long.parseLong(pieces[0]);
            src = Long.parseLong(pieces[1]);
            range = Long.parseLong(pieces[2]);
        }
    }

    static class RangeMap {
        List<Long> starts;
        List<Long> ends;
        List<Long> betweens;

        public RangeMap(List<Range> ranges) {
            starts = new ArrayList<>();
            ends = new ArrayList<>();
            betweens = new ArrayList<>();
            for (Range range : ranges) {
                starts.add(range.src);
                ends.add(range.dst);
                betweens.add(range.range);
            }
        }

        public long convert(long val) {
            for (int i = 0; i < starts.size(); i++) {
                if (starts.get(i) <= val && starts.get(i) + betweens.get(i) > val) {
                    return ends.get(i) + (val - starts.get(i));
                }
            }
            return val;
        }

        public long[] convert2(long val) {
            long nextStart = 10000000000L;
            for (int i = 0; i < starts.size(); i++) {
                if (starts.get(i) > val) {
                    nextStart = Math.min(nextStart, starts.get(i) - val - 1);
                }
                if (starts.get(i) <= val && starts.get(i) + betweens.get(i) > val) {
                    return new long[]{ends.get(i) + (val - starts.get(i)), betweens.get(i) - (val - starts.get(i)) - 1};
                }
            }
            return new long[]{val, nextStart == 10000000000L ? 0 : nextStart};
        }
    }
}