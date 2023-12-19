import java.io.File;
import java.util.*;

public class Main {
    static long answer;
    static Map<String, List<Function>> functions = new HashMap<>();

    public static void main(String[] args) {
        try {
            Scanner reader = new Scanner(new File("input"));
            String input = reader.useDelimiter("\\A").next();
            solve(true, input);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void solve(boolean partOne, String input) {
        String[] sections = input.trim().split("\n\n");
        String[] functionStrings = sections[0].split("\n");
        String[] partStrings = sections[1].split("\n");

        for (String f : functionStrings) {
            String[] functionParts = f.substring(0, f.length() - 1).split("[{]");
            List<Function> functionList = new ArrayList<>();
            for (String fn : functionParts[1].split(",")) {
                functionList.add(new Function(fn));
            }
            functions.put(functionParts[0], functionList);
        }

        if (partOne) {
            List<Part> parts = new ArrayList<>();
            for (String s : partStrings) {
                List<Integer> l = new ArrayList<>();
                String[] a = s.substring(1, s.length() - 1).split(",");
                for (String ss : a) {
                    String[] aa = ss.split("=");
                    l.add(Integer.parseInt(aa[1]));
                }
                parts.add(new Part(l.get(0), l.get(1), l.get(2), l.get(3)));
            }

            for (Part part: parts) execute("in", part);
            System.out.println("Part one: " + answer);
            answer = 0;
        }
    }

    public static void execute(String f, Part p) {
        if (f.equals("A")) {
            answer += p.x + p.m + p.a + p.s;
            return;
        } else if (f.equals("R")) return;
        List<Function> fns = functions.get(f);
        for (Function fn : fns) {
            if (fn.isLessThan()) {
                switch (fn.getRating()) {
                    case 'x' -> {
                        if (p.x < fn.getNumber()) {
                            execute(fn.getJumpTo(), p);
                            return;
                        }
                    }
                    case 'm' -> {
                        if (p.m < fn.getNumber()) {
                            execute(fn.getJumpTo(), p);
                            return;
                        }
                    }
                    case 'a' -> {
                        if (p.a < fn.getNumber()) {
                            execute(fn.getJumpTo(), p);
                            return;
                        }
                    }
                    case 's' -> {
                        if (p.s < fn.getNumber()) {
                            execute(fn.getJumpTo(), p);
                            return;
                        }
                    }
                }
            } else {
                switch (fn.getRating()) {
                    case 'x' -> {
                        if (p.x > fn.getNumber()) {
                            execute(fn.getJumpTo(), p);
                            return;
                        }
                    }
                    case 'm' -> {
                        if (p.m > fn.getNumber()) {
                            execute(fn.getJumpTo(), p);
                            return;
                        }
                    }
                    case 'a' -> {
                        if (p.a > fn.getNumber()) {
                            execute(fn.getJumpTo(), p);
                            return;
                        }
                    }
                    case 's' -> {
                        if (p.s > fn.getNumber()) {
                            execute(fn.getJumpTo(), p);
                            return;
                        }
                    }
                    default -> execute(fn.getJumpTo(), p);
                }
            }
        }
    }

    public record Part(int x, int m, int a, int s) {}
}