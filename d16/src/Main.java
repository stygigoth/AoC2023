import java.io.File;
import java.util.*;

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
        long max = 0;
        Stack<Light> lights = new Stack<>();
        Stack<Light> startingOptions = new Stack<>();
        List<List<Tile>> tiles = new ArrayList<>();
        for (String line : input.trim().split("\n")) {
            List<Tile> tempTiles = new ArrayList<>();
            for (String c : line.split("")) {
                tempTiles.add(new Tile(c));
            }
            tiles.add(tempTiles);
        }

        if (partOne) {
            startingOptions.add(new Light(-1, 0, 'e'));
        } else {
            for (int x = 0; x < tiles.get(0).size(); x++) {
                startingOptions.add(new Light(x, -1, 's'));
                startingOptions.add(new Light(x, tiles.size(), 'n'));
            }

            for (int y = 0; y < tiles.size(); y++) {
                startingOptions.add(new Light(-1, y, 'e'));
                startingOptions.add(new Light(tiles.get(0).size(), y, 'w'));
            }
        }


        while (!startingOptions.isEmpty()) {
            long accum = 0;
            lights.add(startingOptions.pop());

            while (!lights.isEmpty()) {
                Light l = lights.pop();
                switch (l.getD()) {
                    case 'e' -> {
                        if (l.getX() + 1 < tiles.get(0).size()) {
                            Tile t = tiles.get(l.getY()).get(l.getX() + 1);
                            if (t.hasNotBeenVisited('e')) {
                                t.setHasBeenVisited('e');
                                switch (t.getTileType()) {
                                    case MIRRORF -> lights.add(new Light(l.getX() + 1, l.getY(), 'n'));
                                    case MIRRORR -> lights.add(new Light(l.getX() + 1, l.getY(), 's'));
                                    case SPLITTERV -> {
                                        lights.add(new Light(l.getX() + 1, l.getY(), 'n'));
                                        lights.add(new Light(l.getX() + 1, l.getY(), 's'));
                                    }
                                    default -> lights.add(new Light(l.getX() + 1, l.getY(), 'e'));
                                }
                            }
                        }
                    }
                    case 's' -> {
                        if (l.getY() + 1 < tiles.size()) {
                            Tile t = tiles.get(l.getY() + 1).get(l.getX());
                            if (t.hasNotBeenVisited('s')) {
                                t.setHasBeenVisited('s');
                                switch (t.getTileType()) {
                                    case MIRRORF -> lights.add(new Light(l.getX(), l.getY() + 1, 'w'));
                                    case MIRRORR -> lights.add(new Light(l.getX(), l.getY() + 1, 'e'));
                                    case SPLITTERH -> {
                                        lights.add(new Light(l.getX(), l.getY() + 1, 'e'));
                                        lights.add(new Light(l.getX(), l.getY() + 1, 'w'));
                                    }
                                    default -> lights.add(new Light(l.getX(), l.getY() + 1, 's'));
                                }
                            }
                        }
                    }
                    case 'w' -> {
                        if (l.getX() - 1 >= 0) {
                            Tile t = tiles.get(l.getY()).get(l.getX() - 1);
                            if (t.hasNotBeenVisited('w')) {
                                t.setHasBeenVisited('w');
                                switch (t.getTileType()) {
                                    case MIRRORF -> lights.add(new Light(l.getX() - 1, l.getY(), 's'));
                                    case MIRRORR -> lights.add(new Light(l.getX() - 1, l.getY(), 'n'));
                                    case SPLITTERV -> {
                                        lights.add(new Light(l.getX() - 1, l.getY(), 'n'));
                                        lights.add(new Light(l.getX() - 1, l.getY(), 's'));
                                    }
                                    default -> lights.add(new Light(l.getX() - 1, l.getY(), 'w'));
                                }
                            }
                        }
                    }
                    case 'n' -> {
                        if (l.getY() - 1 >= 0) {
                            Tile t = tiles.get(l.getY() - 1).get(l.getX());
                            if (t.hasNotBeenVisited('n')) {
                                t.setHasBeenVisited('n');
                                switch (t.getTileType()) {
                                    case MIRRORF -> lights.add(new Light(l.getX(), l.getY() - 1, 'e'));
                                    case MIRRORR -> lights.add(new Light(l.getX(), l.getY() - 1, 'w'));
                                    case SPLITTERH -> {
                                        lights.add(new Light(l.getX(), l.getY() - 1, 'e'));
                                        lights.add(new Light(l.getX(), l.getY() - 1, 'w'));
                                    }
                                    default -> lights.add(new Light(l.getX(), l.getY() - 1, 'n'));
                                }
                            }
                        }
                    }
                }
            }

            for (List<Tile> row : tiles) {
                for (Tile t : row) {
                    if (t.hasBeenVisited()) accum++;
                    t.setNotHasBeenVisited();
                }
            }

            if (accum > max) {
                max = accum;
            }
        }

        if (partOne) {
            System.out.println("Part one: " + max);
        } else {
            System.out.println("Part two: " + max);
        }
    }
}