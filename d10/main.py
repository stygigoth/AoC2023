import numpy as np
import shapely
from rasterio.features import rasterize
shapes = {
    "|": ((.5, 1), (.5, 0)),
    "-": ((0, .5), (1, .5)),
    "L": ((.5, 1), (.5, .5), (1, .5)),
    "J": ((.5, 1), (.5, .5), (0, .5)),
    "7": ((.5, 0), (.5, .5), (0, .5)),
    "F": ((.5, 0), (.5, .5), (1, .5)),
}
shapes = {
    k: np.array(v)
    for k, v in shapes.items()
}
def make_line(shape: str, crds: np.ndarray[int]) -> shapely.LineString:
    try:
        return shapely.LineString(shapes[shape] + crds)
    except KeyError:
        return None
data = np.loadtxt("input", object)
data = np.array([[*s] for s in data])
coords = np.indices(data.shape)[::-1,::-1,:]
lines = [
    make_line(shp, crds)
    for shp, crds in zip(
        data.ravel(),
        np.stack(coords, axis=-1).reshape(-1, 2),
    )
]
lines = [*shapely.line_merge(shapely.union_all(lines)).geoms]
loop = lines[np.argmax([*map(shapely.length, lines)])]
print("Part one: ", np.ceil(loop.length/2))
loop_mask = rasterize(
    [loop],
    data.shape,
    all_touched=True,
)
poly_mask = rasterize(
    [shapely.Polygon(loop)],
    data.shape,
    all_touched=True,
)
print("Part two: ", (poly_mask-loop_mask).sum())
