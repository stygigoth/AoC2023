patterns = list(map(str.split, open("input").read().split("\n\n")))

def solve(pattern):
    for i in range(len(pattern)):
        if sum(c != d for l,m in zip(pattern[i-1::-1], pattern[i:]) for c,d in zip(l, m)) == s:
            return i
    return 0

for s in 0,1:
    print(sum(100 * solve(pattern) + solve([*zip(*pattern)]) for pattern in patterns))
