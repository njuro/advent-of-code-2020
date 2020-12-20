import utils.Coordinate
import utils.Direction
import utils.readInputBlock
import utils.toStringRepresentation
import kotlin.math.sqrt

typealias Tile = Map<Coordinate, Char>

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
            val transformations = mutableSetOf<Tile>()
            var current = tile
            repeat(4) {
                current = current.rotate()
                transformations.add(current)
                transformations.add(current.flipVertically())
                transformations.add(current.flipHorizontally())
            }
            key to transformations
        }.toMap()

        val edges = variations.mapValues { (_, tiles) -> tiles.flatMap { it.allEdges() }.toSet() }

        val candidates = tiles.map { (key, tile) ->
            val others = edges.filterKeys { it != key }
            val neighbours = Direction.values().associateWith { direction ->
                val edge = tile.edge(direction)
                others.filterValues { it.contains(edge) }.keys.firstOrNull()
            }

            key to neighbours.filterValues { it != null }
        }.toMap()

        if (!part2) {
            return candidates.filterValues { it.size == 2 }.keys.fold(1L) { a, b -> a * b }
        }

        fun resolveNext(current: Pair<Int, Tile>, direction: Direction): Pair<Int, Tile> {
            val edge = current.second.edge(direction)
            variations.filterKeys { it != current.first }.forEach { (id, variations) ->
                variations.forEach { variation ->
                    if (edge == variation.edge(direction.turnOpposite())) {
                        return id to variation
                    }
                }
            }

            throw IllegalArgumentException()
        }

        val start = candidates.filterValues { it.keys == setOf(Direction.LEFT, Direction.DOWN) }.keys.first()
        val grid = mutableListOf(start to tiles[start]!!)
        var current = grid.first()
        var rowStart = current
        while (grid.size != tiles.size) {
            val index = grid.size % dimension
            rowStart = if (index == 1) current else rowStart
            current = if (index == 0) resolveNext(rowStart, Direction.DOWN) else resolveNext(current, Direction.LEFT)
            grid.add(current)
        }

        return -1
    }

    private fun Tile.edge(direction: Direction) = when (direction) {
        Direction.UP -> topEdge()
        Direction.RIGHT -> rightEdge()
        Direction.DOWN -> bottomEdge()
        Direction.LEFT -> leftEdge()
    }

    private fun Tile.topEdge() =
        filterKeys { it.y == 0 }.toSortedMap(Comparator.comparing { it.x }).values.joinToString("")

    private fun Tile.rightEdge() =
        filterKeys { it.x == maxCoordinate }.toSortedMap(Comparator.comparing { it.y }).values.joinToString("")

    private fun Tile.bottomEdge() =
        filterKeys { it.y == maxCoordinate }.toSortedMap(Comparator.comparing { it.x }).values.joinToString("")

    private fun Tile.leftEdge() =
        filterKeys { it.x == 0 }.toSortedMap(Comparator.comparing { it.y }).values.joinToString("")

    private fun Tile.allEdges() = setOf(topEdge(), rightEdge(), bottomEdge(), leftEdge())

    private fun Tile.layout() = toStringRepresentation(true, separator = "")

    private fun Tile.rotate() = (0..maxCoordinate).flatMap { x ->
        (0..maxCoordinate).map { y ->
            Coordinate(x, y) to get(Coordinate(maxCoordinate - y, x))!!
        }
    }.toMap()

    private fun Tile.flipVertically() = mapKeys { (coordinate, _) ->
        coordinate.copy(y = maxCoordinate - coordinate.y)
    }

    private fun Tile.flipHorizontally() = mapKeys { (coordinate, _) ->
        coordinate.copy(x = maxCoordinate - coordinate.x)
    }
}

fun main() {
    print(Day20Fix().run(part2 = true))
}