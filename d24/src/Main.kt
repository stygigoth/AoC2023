import java.io.File
import java.util.*
import com.microsoft.z3.*;
fun main() {
    Main().main()
}

class Main {
    private val file = File("input")
    data class Point3D(val x: Double, val y: Double, val z: Double)
    data class Velocity3D(val x: Double, val y: Double, val z: Double)
    data class Hailstone(val p: Point3D, val v: Velocity3D) {
        fun slope() = v.y / v.x
    }
    fun main() {
        try {
            solve(true)
            solve(false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun solve(partOne: Boolean) {
        val hail = file.readLines().map { ita ->
            ita.split(" @ ").let { it ->
                Hailstone(it[0].split(", ").map { it.trim().toDouble() }.let { (x,y,z) -> Point3D(x,y,z) },
                    it[1].split(", ").map { it.trim().toDouble() }.let { (x,y,z) -> Velocity3D(x,y,z) })
            }
        }

        if (partOne) {
            println("Part one: " + hail.combinations(2).count { calc2DOverlap(it[0], it[1]) })
        } else {
            val ctx = Context(HashMap<String, String>().also {
                it["proof"] = "true"
            })
            val solver = ctx.mkSolver()
            val (x, y, z) = listOf("x", "y", "z").map { ctx.mkIntConst(it) }
            val (vx, vy, vz) = listOf("vx", "vy", "vz").map { ctx.mkIntConst(it) }

            (0..2).forEach { idx ->
                val h = hail[idx]
                val t = ctx.mkIntConst("t$idx")
                solver.add(ctx.mkEq(ctx.mkAdd(x, ctx.mkMul(vx, t)), ctx.mkAdd(ctx.mkInt(h.p.x.toLong()), ctx.mkMul(ctx.mkInt(h.v.x.toLong()), t))))
                solver.add(ctx.mkEq(ctx.mkAdd(y, ctx.mkMul(vy, t)), ctx.mkAdd(ctx.mkInt(h.p.y.toLong()), ctx.mkMul(ctx.mkInt(h.v.y.toLong()), t))))
                solver.add(ctx.mkEq(ctx.mkAdd(z, ctx.mkMul(vz, t)), ctx.mkAdd(ctx.mkInt(h.p.z.toLong()), ctx.mkMul(ctx.mkInt(h.v.z.toLong()), t))))
            }

            if (solver.check() == Status.SATISFIABLE) {
                println("Part two: " + solver.model.eval(ctx.mkAdd(x, ctx.mkAdd(y, z)), false))
            }
        }
    }

    private fun calc2DOverlap(h1: Hailstone, h2: Hailstone): Boolean {
        fun validFuture(h: Hailstone, dx: Double, dy: Double): Boolean {
            return !((h.v.x < 0 && h.p.x < dx) || (h.v.x > 0 && h.p.x > dx) || (h.v.y < 0 && h.p.y < dy) || (h.v.y > 0 && h.p.y > dy))
        }

        if (h1.slope() == h2.slope()) return false
        val dx = ((h2.slope() * h2.p.x) - (h1.slope() * h1.p.x) + h1.p.y - h2.p.y) / (h2.slope() - h1.slope())
        val dy = (h1.slope() * (dx - h1.p.x)) + h1.p.y
        val valid = validFuture(h1, dx, dy) && validFuture(h2, dx, dy)

        return dx in 200000000000000.0..400000000000000.0 && dy in 200000000000000.0..400000000000000.0 && valid
    }

    private fun <T> List<T>.combinations(size: Int): List<List<T>> = when (size) {
        0 -> listOf(listOf())
        else -> flatMapIndexed { idx, element -> drop(idx+1).combinations(size - 1).map { listOf(element) + it }}
    }
}

