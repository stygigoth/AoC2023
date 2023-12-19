import java.util.Objects;

public class Function {
    private final char rating;
    private final boolean lessThan;
    private final int number;
    private final String jumpTo;

    public Function(String s) {
        if (s.length() < 4) {
            rating = 'q';
            lessThan = false;
            number = 4000;
            jumpTo = s;
        } else {
            rating = s.charAt(0);
            lessThan = s.charAt(1) == '<';
            number = Integer.parseInt(s.substring(2, s.indexOf(':')));
            jumpTo = s.substring(s.indexOf(':') + 1);
        }
    }

    public char getRating() {
        return rating;
    }

    public boolean isLessThan() {
        return lessThan;
    }

    public int getNumber() {
        return number;
    }

    public String getJumpTo() {
        return jumpTo;
    }

    public String toString() {
        return rating + " " + lessThan + " " + getNumber() + "->" + jumpTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function = (Function) o;
        return rating == function.rating && lessThan == function.lessThan && number == function.number && Objects.equals(jumpTo, function.jumpTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, lessThan, number, jumpTo);
    }
}
