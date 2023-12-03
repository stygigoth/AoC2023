import java.io.File

fun main() {
    val input = File("input").readText().split("\n")
    val map = HashMap<Pair<Int, Int>, Int>()
    for (i in input.indices) {
        val s = input[i].split("").drop(1).dropLast(1)
        var j = 0
        while (j < s.size) {
            var p = false
            val k = j
            var n = 0
            while (s[j].toCharArray()[0].isDigit()) {
                p = true
                n *= 10
                n += s[j].toInt()
                j++
                if (j >= s.size) {
                    break;
                }
            }
            if (n > 0) {
                map[Pair(k, i)] = n
            }
            if (!p) j++
        }
    }

    partOne(map, input)
    partTwo(input)
}

fun partOne(map: HashMap<Pair<Int, Int>, Int>, input: List<String>) {
    var sum = 0

    map.forEach {
        var br = false
        for (i in it.key.second - 1..it.key.second + 1) {
            for (j in it.key.first - 1..it.key.first + it.value.toString().length) {
                val char = input[i.coerceIn(0, input.size - 1)].split("").drop(1).dropLast(1)[j.coerceIn(0, input[0].length - 1)]
                if (char != "." && !char.toCharArray()[0].isDigit()) {
                    sum += it.value
                    br = true
                    break
                }
            }
            if (br) {
                break
            }
        }
    }

    println(sum)
}

fun findNeighborNumbers(input: List<String>, position: Pair<Int, Int>): List<Pair<Pair<Int, Int>, Int>> {
    fun getNumberAtPosition(input: List<String>, position: Pair<Int, Int>): Pair<Pair<Int, Int>, Int> {
        val (x, y) = position
        if (!input[y][x].isDigit()) {
            throw Error("Expected '${input[y][x]}' at $x, $y to be digit")
        }

        val digits = mutableListOf<Char>()

        var lowestX = x

        var nX = x
        while (nX > -1 && input[y][nX].isDigit()) {
            digits.add(0, input[y][nX])
            lowestX = nX
            nX -= 1
        }

        nX = x + 1
        while (nX < input[y].length && input[y][nX].isDigit()) {
            digits.add(input[y][nX])
            nX += 1
        }

        val n = digits.joinToString("").toInt()
        return Pair(Pair(lowestX, y), n)
    }

    val (x, y) = position
    val foundNumbers = mutableListOf<Pair<Pair<Int, Int>, Int>> ()
    // Checking N
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x, y-1)))
    } catch (e: Error) {
        e.printStackTrace()
    }

    // Checking NE
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x+1, y-1)))
    } catch (e: Error) {
        e.printStackTrace()
    }

    // Checking E
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x+1, y)))
    } catch (e: Error) {
        e.printStackTrace()
    }

    // Checking SE
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x+1, y+1)))
    } catch (e: Error) {
        e.printStackTrace()
    }

    // Checking S
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x, y+1)))
    } catch (e: Error) {
        e.printStackTrace()
    }

    // Checking SW
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x-1, y+1)))
    } catch (e: Error) {
        e.printStackTrace()
    }

    // Checking W
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x-1, y)))
    } catch (e: Error) {
        e.printStackTrace()
    }

    // Checking NW
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x-1, y-1)))
    } catch (e: Error) {
        e.printStackTrace()
    }


    return foundNumbers
}

fun partTwo(input: List<String>) {
    var sum = 0

    input.forEachIndexed { idx, s ->
        s.forEachIndexed { index, c ->
            if (!c.isDigit() && c == '*') {
                val neighbors = findNeighborNumbers(input, Pair(index, idx))
                val neighborsSet = HashMap<Pair<Int, Int>, Int>()
                neighbors.forEach {
                    val (position, value) = it
                    neighborsSet[position] = value
                }
                if (neighborsSet.size == 2) sum += neighborsSet.values.reduce { acc, i -> acc * i}
            }
        }
    }

    println(sum)
}