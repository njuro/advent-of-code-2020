import utils.Coordinate
import utils.readInputLines
import utils.toStringRepresentation

/** [https://adventofcode.com/2020/day/11] */
class FerrySeats : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        var current = readInputLines("11.txt")
            .flatMapIndexed { y, row -> row.mapIndexed { x, c -> Coordinate(x, y) to c } }.toMap().withDefault { '.' }
        val seen = mutableSetOf<String>()
        var hash = ""

        while (!seen.contains(hash)) {
            seen.add(hash)
            val updated = current.toMutableMap().withDefault { '.' }
            current.filterValues { it != '.' }.forEach { (coordinate, seat) ->
                val occupied = countOccupied(current, coordinate, ignoreFloor = part2)
                if (seat == 'L' && occupied == 0) {
                    updated[coordinate] = '#'
                }
                if (seat == '#' && occupied >= if (part2) 5 else 4) {
                    updated[coordinate] = 'L'
                }
            }
            hash = updated.toStringRepresentation(offsetCoordinates = true)
            current = updated
        }

        return current.filterValues { it == '#' }.size
    }

    private fun countOccupied(map: Map<Coordinate, Char>, coordinate: Coordinate, ignoreFloor: Boolean): Int {
        val directions = mutableSetOf<(Coordinate) -> Coordinate>(
            { c -> c.up() },
            { c -> c.right() },
            { c -> c.down() },
            { c -> c.left() },
            { c -> c.up().right() },
            { c -> c.up().left() },
            { c -> c.down().right() },
            { c -> c.down().left() },
        )

        return directions.count { move ->
            var current = coordinate
            do {
                current = move(current)
            } while (if (ignoreFloor) map.containsKey(current) && map[current] == '.' else false)
            map.getValue(current) == '#'
        }
    }
}

fun main() {
    print(FerrySeats().run(part2 = true))
}