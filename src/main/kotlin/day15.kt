import utils.readInputBlock

/** [https://adventofcode.com/2020/day/15] */
class MemoryGame : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val starting = readInputBlock("15.txt").split(",").map(String::toInt)
        val memory = starting.mapIndexed { index, number ->
            number to (index + 1 to -1)
        }.toMap().toMutableMap().withDefault { (-1 to -1) }

        var last = starting.last()
        for (turn in (starting.size + 1)..(if (part2) 30000000 else 2020)) {
            val (current, previous) = memory.getValue(last)
            last = if (previous == -1) 0 else current - previous
            memory[last] = turn to memory.getValue(last).first
        }

        return last
    }
}

fun main() {
    print(MemoryGame().run(part2 = true))
}