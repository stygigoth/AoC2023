public class Light {
    private int x, y;
    private final char d;

    public Light(int x, int y, char d) {
        this.x = x;
        this.y = y;
        this.d = d;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getD() {
        return d;
    }
}
