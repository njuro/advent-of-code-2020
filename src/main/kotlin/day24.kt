import utils.readInputLines

/** [https://adventofcode.com/2020/day/24] */
class HexGrid : AdventOfCodeTask {

    data class CubeCoordinate(val x: Int, val y: Int, val z: Int) {
        fun adjacent() = mapOf(
            "sw" to CubeCoordinate(x - 1, y, z + 1),
            "se" to CubeCoordinate(x, y - 1, z + 1),
            "nw" to CubeCoordinate(x, y + 1, z - 1),
            "ne" to CubeCoordinate(x + 1, y, z - 1),
            "w" to CubeCoordinate(x - 1, y + 1, z),
            "e" to CubeCoordinate(x + 1, y - 1, z),
        )
    }

    override fun run(part2: Boolean): Any {
        var flipped = mutableSetOf<CubeCoordinate>()
        readInputLines("24.txt").forEach { instructions ->
            var last = ""
            val coordinate = instructions.map(Char::toString).fold(CubeCoordinate(0, 0, 0)) { current, c ->
                if (c in setOf("s", "n")) {
                    current.also {
                        last = c
                    }
                } else {
                    current.adjacent()["$last$c"]!!.also {
                        last = ""
                    }
                }
            }
            if (coordinate in flipped) {
                flipped.remove(coordinate)
            } else {
                flipped.add(coordinate)
            }
        }

        if (part2) {
            repeat(100) {
                val updated = flipped.toMutableSet()
                val blackNeighbours = mutableMapOf<CubeCoordinate, Int>().withDefault { 0 }
                flipped.forEach { coordinate ->
                    val neighbours = coordinate.adjacent().values
                    val count = neighbours.count { it in flipped }
                    if (count == 0 || count > 2) {
                        updated.remove(coordinate)
                    }
                    neighbours.forEach { blackNeighbours[it] = blackNeighbours.getValue(it) + 1 }
                }
                updated.addAll(blackNeighbours.filter { it.value == 2 }.keys)
                flipped = updated
            }
        }

        return flipped.size
    }
}

fun main() {
    print(HexGrid().run(part2 = true))
}