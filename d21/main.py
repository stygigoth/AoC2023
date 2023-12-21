import numpy as np
P = complex
class Grid:
    def __init__(self, input):
        self.size = len(input.splitlines())
        self.grid = set()
        self.positions = set()
        for y, l in enumerate(input.splitlines()):
            for x, c in enumerate(l):
                if c == "#":
                    self.grid.add(P(x,y))
                if c == "S":
                    self.positions.add(P(x,y))
    def step(self):
        new_pos = set()
        for p in self.positions:
            for d in (1,-1,1j,-1j):
                if self.wrap(p+d) not in self.grid:
                    new_pos.add(p+d)
        self.positions = new_pos
    def wrap(self, p):
        return P(p.real%self.size, p.imag%self.size)

with open("input") as f:
    g = Grid(f.read().strip())
    X,Y = [0,1,2], []
    target = (26501365 - 65)//131
    for s in range(65 + 131*2 + 1):
        if s % 131 == 65:
            Y.append(len(g.positions))
        if s == 64:
            print("Part one:",len(g.positions))
        g.step()
    poly = np.rint(np.polynomial.polynomial.polyfit(X,Y,2)).astype(int).tolist()
    print("Part two:",sum(poly[i]*target**i for i in range(3)))
