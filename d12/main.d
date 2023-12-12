import std;

void main() {
    solve(true);
    solve(false);
}

void solve(bool part_one) {
    long answer = 0;
    auto file = File("input");

    foreach(ref b; file.byLineCopy) {
        auto t = b.split;
        auto s = t[0];
        if (!part_one) {
            s = s.repeat(5).join('?');
        }
        s ~= '.';
        auto a = t[1].strip.split(",").map!(to!(int)).array;
        if (!part_one) {
            a = a.repeat(5).join;
        }
        auto n = s.length.to!(int);
        auto k = a.length.to!(int);
        a ~= n + 1;

        auto f = new long[][][] (n + 1, k + 2, n + 2);
        f[0][0][0] = 1;
        foreach(i ; 0..n) {
            foreach(j ; 0..k+1) {
                foreach(p ; 0..n+1) {
                    auto current = f[i][j][p];
                    if (!current) continue;
                    if (s[i] == '.' || s[i] == '?') {
                        if (p == 0 || p == a[j - 1]) {
                            f[i + 1][j][0] += current;
                        }
                    }
                    if (s[i] == '#' || s[i] == '?') {
                        f[i + 1][j + !p][p + 1] += current;
                    }
                }
            }
        }
        answer += f[n][k][0];
    }
    if (part_one) {
        writeln("Part one: ", answer);
    } else {
        writeln("Part two: ", answer);
    }
}
