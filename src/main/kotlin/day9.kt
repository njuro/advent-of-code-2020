import utils.readInputLines

/** [https://adventofcode.com/2020/day/9] */
class FindSumYetAgain : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val input = readInputLines("9.txt").map { it.toLong() }
        val invalid = findInvalid(input)
        return if (part2) findSum(input, invalid) else invalid
    }

    private fun findInvalid(input: List<Long>, preamble: Int = 25): Long {
        return input[(preamble until input.size).first { i ->
            val subset = input.subList(i - preamble, i).toSet()
            subset.all { !subset.contains(input[i] - it) }
        }]
    }

    private fun findSum(input: List<Long>, invalid: Long): Long {
        var index = 0
        val sequence = mutableListOf<Long>()
        do {
            sequence.add(input[index++])
            while (sequence.sum() > invalid) {
                sequence.removeFirst()
            }
        } while (sequence.sum() != invalid)

        return sequence.minOrNull()!! + sequence.maxOrNull()!!
    }
}

fun main() {
    println(FindSumYetAgain().run(part2 = true))
}