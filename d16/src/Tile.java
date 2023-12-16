public class Tile {
    private final boolean[] hasBeenVisited = new boolean[4];
    private final TileType tileType;

    public Tile(String type) {
        this.tileType = switch (type) {
            case "/" -> TileType.MIRRORF;
            case "\\" -> TileType.MIRRORR;
            case "|" -> TileType.SPLITTERV;
            case "-" -> TileType.SPLITTERH;
            default -> TileType.EMPTY;
        };
    }

    public void setHasBeenVisited(char d) {
        switch (d) {
            case 'e' -> hasBeenVisited[0] = true;
            case 's' -> hasBeenVisited[1] = true;
            case 'w' -> hasBeenVisited[2] = true;
            case 'n' -> hasBeenVisited[3] = true;
        }
    }

    public boolean hasNotBeenVisited(char d) {
        return !switch (d) {
            case 'e' -> hasBeenVisited[0];
            case 's' -> hasBeenVisited[1];
            case 'w' -> hasBeenVisited[2];
            case 'n' -> hasBeenVisited[3];
            default -> false;
        };
    }

    public boolean hasBeenVisited() {
        return hasBeenVisited[0] || hasBeenVisited[1] || hasBeenVisited[2] || hasBeenVisited[3];
    }

    public void setNotHasBeenVisited() {
        hasBeenVisited[0] = false;
        hasBeenVisited[1] = false;
        hasBeenVisited[2] = false;
        hasBeenVisited[3] = false;
    }

    public TileType getTileType() {
        return tileType;
    }

    public enum TileType {
        EMPTY,
        MIRRORF,
        MIRRORR,
        SPLITTERV,
        SPLITTERH;
    }
}
