import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File in = new File("/home/nyx/IdeaProjects/Aoc2023D2/src/input");
            Scanner reader = new Scanner(in);
            int r = 0;
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                r += getVal(data);
            }
            reader.close();
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getVal(String data) {
        boolean ret = true;
        String[] game = data.split(": ");
        int id = Integer.parseInt(game[0].substring(5));
        String[] draws = game[1].split("; ");

        int mr = 12;
        int mg = 13;
        int mb = 14;
        int maxR = 0;
        int maxG = 0;
        int maxB = 0;

        for (String draw : draws) {
            int r = 0;
            int g = 0;
            int b = 0;
            String[] d = draw.split(", ");
            for (String dr : d) {
                char[] drw = new char[dr.length()];
                for (int i = 0; i < dr.length(); i++) {
                    drw[i] = dr.charAt(i);
                }
                int n = 0;
                boolean bl = false;
                for (char c : drw) {
                    boolean bl2 = false;
                    if (Character.isDigit(c) && !bl) {
                        n *= 10;
                        n += Integer.parseInt(String.valueOf(c));
                    } else if (Character.isWhitespace(c)) {
                        bl = true;
                    } else {
                        switch (c) {
                            case 'r' -> {
                                r += n;
                                bl2 = true;
                            }
                            case 'g' -> {
                                g += n;
                                bl2 = true;
                            }
                            case 'b' -> {
                                b += n;
                                bl2 = true;
                            }
                            default -> {}
                        }
                    }
                    if (bl2) break;
                }
            }
            maxR = Math.max(maxR, r);
            maxG = Math.max(maxG, g);
            maxB = Math.max(maxB, b);
        }

        return maxR*maxG*maxB;
    }
}
