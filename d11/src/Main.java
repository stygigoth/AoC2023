import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner reader = new Scanner(new File("input"));
            String input = reader.useDelimiter("\\A").next();
            solve(true, input);
            solve(false, input);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void solve(boolean partOne, String input) {
        long answer = 0;
        List<Boolean> rowsEmpty = new ArrayList<>();
        List<Boolean> colsEmpty = new ArrayList<>();
        List<Point> points = new ArrayList<>();
        String[] rows = input.split("\n");
        char[][] map = new char[rows[0].length()][rows.length];
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == '#') {
                    points.add(new Point(j, i));
                }
                map[i][j] = row.charAt(j);
            }
        }
        for (int j = 0; j < map[0].length; j++) {
            boolean b = true;
            for (char[] chars : map) {
                if (chars[j] == '#') {
                    b = false;
                    break;
                }
            }
            colsEmpty.add(b);
        }
        for (char[] chars : map) {
            boolean b = true;
            for (char aChar : chars) {
                if (aChar == '#') {
                    b = false;
                    break;
                }
            }
            rowsEmpty.add(b);
        }
        for (Point p : points) {
            p.setTested();
            for (Point p1 : points) {
                if (p1.tested) continue;
                int lowX = Math.min(p.x, p1.x);
                int lowY = Math.min(p.y, p1.y);
                int highX = Math.max(p.x, p1.x);
                int highY = Math.max(p.y, p1.y);
                int distX = highX - lowX;
                int distY = highY - lowY;
                int dist = distY + distX + calculateDistAddition(partOne, lowX, lowY, distX, distY, rowsEmpty, colsEmpty);
                answer += dist;
            }
        }
        if (partOne) {
            System.out.println("Part one: " + answer);
        } else {
            System.out.println("Part two: " + answer);
        }
    }

    public static int calculateDistAddition(boolean partOne, int x, int y, int dx, int dy, List<Boolean> emptyRows, List<Boolean> emptyCols) {
        int d = partOne ? 1 : 999999;
        int r = 0;
        for (int i = x; i < x + dx; i++) {
            if (emptyCols.get(i)) r += d;
        }
        for (int i = y; i < y + dy; i++) {
            if (emptyRows.get(i)) r += d;
        }
        return r;
    }

    public static class Point {
        final int x, y;
        boolean tested;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setTested() {
            tested = true;
        }
    }
}