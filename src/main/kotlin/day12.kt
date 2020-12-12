import utils.Coordinate
import utils.Direction
import utils.readInputLines

/** [https://adventofcode.com/2020/day/12] */
class Navigation : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val pattern = Regex("([NSEWLRF])(\\d+)")
        val instructions = readInputLines("12.txt").map {
            val (operation, value) = pattern.matchEntire(it)!!.destructured
            operation[0] to value.toInt()
        }

        var direction = Direction.RIGHT
        var waypoint = Coordinate(10, 1)
        var coordinate = Coordinate(0, 0)
        instructions.forEach { (operation, value) ->
            when (operation) {
                'N' ->
                    if (part2) {
                        waypoint = waypoint.up(value)
                    } else {
                        coordinate = coordinate.up(value)
                    }
                'S' ->
                    if (part2) {
                        waypoint = waypoint.down(value)
                    } else {
                        coordinate = coordinate.down(value)
                    }
                'E' ->
                    if (part2) {
                        waypoint = waypoint.right(value)
                    } else {
                        coordinate = coordinate.right(value)
                    }
                'W' ->
                    if (part2) {
                        waypoint = waypoint.left(value)
                    } else {
                        coordinate = coordinate.left(value)
                    }
                'L' ->
                    repeat(value / 90) {
                        if (part2) {
                            waypoint = waypoint.copy(x = waypoint.y * -1, y = waypoint.x)
                        } else {
                            direction = direction.turnLeft()
                        }
                    }
                'R' ->
                    repeat(value / 90) {
                        if (part2) {
                            waypoint = waypoint.copy(x = waypoint.y, y = waypoint.x * -1)
                        } else {
                            direction = direction.turnRight()
                        }
                    }
                'F' ->
                    repeat(value) {
                        if (part2) {
                            coordinate += waypoint
                        } else {
                            coordinate = coordinate.move(direction)
                        }
                    }
            }
        }

        return coordinate.distanceToCenter()
    }
}

fun main() {
    print(Navigation().run(part2 = false))
}