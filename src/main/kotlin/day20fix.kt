import utils.Coordinate
import utils.Direction
import utils.readInputBlock
import utils.toStringRepresentation
import kotlin.math.sqrt

/** [https://adventofcode.com/2020/day/20] */
class Day20Fix : AdventOfCodeTask {

    private var maxCoordinate = 9

    override fun run(part2: Boolean): Any {
        val tiles = readInputBlock("20.txt").split("\n\n").map {
            val lines = it.split("\n")
            val (id) = Regex("Tile (\\d+):").matchEntire(lines.first())!!.destructured
            id.toInt() to lines.drop(1).flatMapIndexed { y, row ->
                row.mapIndexed { x, c -> Coordinate(x, y) to c }
            }.toMap()
        }.toMap()
        val dimension = sqrt(tiles.size.toDouble()).toInt()

        val variations = tiles.map { (key, tile) ->
            val transformations = mutableSetOf<Map<Coordinate, Char>>()
            var current = tile
            repeat(4) {
                current = current.rotate()
                transformations.add(current)
                transformations.add(current.flipVertically())
            }
            key to transformations
        }.toMap()

        val edges = variations.mapValues { (_, tiles) -> tiles.flatMap { it.allEdges() }.toSet() }

        val candidates = tiles.map { (key, tile) ->
            val others = edges.filterKeys { it != key }
            val neighbours = Direction.values().associateWith { direction ->
                val edge = tile.edge(direction)
                others.filterValues { it.contains(edge) }.keys
            }

            key to neighbours.filterValues { it.isNotEmpty() }
        }.toMap()

        if (!part2) {
            return candidates.filterValues { it.size == 2 }.keys.fold(1L) { a, b -> a * b }
        }

        return -1
    }

    private fun Map<Coordinate, Char>.edge(direction: Direction) = when (direction) {
        Direction.UP -> topEdge()
        Direction.RIGHT -> rightEdge()
        Direction.DOWN -> bottomEdge()
        Direction.LEFT -> leftEdge()
    }

    private fun Map<Coordinate, Char>.topEdge() = filterKeys { it.y == 0 }.values.joinToString("")

    private fun Map<Coordinate, Char>.rightEdge() = filterKeys { it.x == maxCoordinate }.values.joinToString("")

    private fun Map<Coordinate, Char>.bottomEdge() = filterKeys { it.y == maxCoordinate }.values.joinToString("")

    private fun Map<Coordinate, Char>.leftEdge() = filterKeys { it.x == 0 }.values.joinToString("")

    private fun Map<Coordinate, Char>.allEdges() = setOf(topEdge(), rightEdge(), bottomEdge(), leftEdge())

    private fun Map<Coordinate, Char>.layout() = toStringRepresentation(true, separator = "")

    private fun Map<Coordinate, Char>.rotate() = (0..maxCoordinate).flatMap { x ->
        (0..maxCoordinate).map { y ->
            Coordinate(x, y) to get(Coordinate(maxCoordinate - y, x))!!
        }
    }.toMap()

    private fun Map<Coordinate, Char>.flipVertically() = mapKeys { (coordinate, _) ->
        coordinate.copy(y = maxCoordinate - coordinate.y)
    }

    private fun Map<Coordinate, Char>.flipHorizontally() = mapKeys { (coordinate, _) ->
        coordinate.copy(x = maxCoordinate - coordinate.x)
    }
}

fun main() {
    print(Day20Fix().run(part2 = false))
}