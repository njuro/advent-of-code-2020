import utils.Coordinate
import utils.maxX
import utils.maxY
import utils.readInputLines

/** [https://adventofcode.com/2020/day/3] */
class TreeSlopes : AdventOfCodeTask {

    private val map = mutableMapOf<Coordinate, Char>().withDefault { ' ' }

    override fun run(part2: Boolean): Any {
        readInputLines("3.txt").forEachIndexed { y, row -> row.forEachIndexed { x, c -> map[Coordinate(x, y)] = c } }
        val slopes = if (part2) setOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2) else setOf(3 to 1)
        var result = 1L

        for ((dx, dy) in slopes) {
            var trees = 0L
            var current = Coordinate(0, 0)
            while (current.y <= map.maxY()) {
                current = current.copy(x = (current.x + dx) % (map.maxX() + 1), y = current.y + dy)
                if (map.getValue(current) == '#') {
                    trees++
                }
            }

            result *= trees
        }


        return result
    }
}

fun main() {
    println(TreeSlopes().run(part2 = true))
}