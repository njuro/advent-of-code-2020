import utils.readInputLines
import kotlin.math.ceil

/** [https://adventofcode.com/2020/day/5] */
class AirplaneSeats : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val seats = readInputLines("5.txt").map(::calculateSeat)
        if (!part2) {
            return seats.maxOrNull()!!
        }

        val maxId = 127 * 8 + 7
        val missing = (0..maxId).toMutableSet().apply {
            removeAll(seats)
        }

        return missing.first { !missing.contains(it - 1) && !missing.contains(it + 1) }
    }

    private fun calculateSeat(pass: String): Int {
        val rowInstructions = pass.substring(0, 7).map { c -> c == 'B' }
        val rowNumber = calculateSeat(rowInstructions, 127)
        val columnInstructions = pass.substring(7).map { c -> c == 'R' }
        val columnNumber = calculateSeat(columnInstructions, 7)

        return rowNumber * 8 + columnNumber
    }

    private fun calculateSeat(instructions: List<Boolean>, max: Int): Int {
        var low = 0
        var high = max

        instructions.forEach { upper ->
            val mid = ceil((low + high) / 2.0).toInt()
            if (upper) {
                low = mid
            } else {
                high = mid - 1
            }
        }

        return low
    }
}

fun main() {
    println(AirplaneSeats().run(part2 = true))
}