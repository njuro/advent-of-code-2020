import utils.readInputLines

/** [https://adventofcode.com/2020/day/10] */
class Adapters : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val adapters = readInputLines("10.txt").map { it.toInt() }.toMutableList().apply {
            add(maxOrNull()!! + 3)
        }.sorted()

        return if (part2) {
            val paths = mutableMapOf<Int, Long>().withDefault { 0L }.apply { put(0, 1) }
            adapters.forEach { paths[it] = paths.getValue(it - 1) + paths.getValue(it - 2) + paths.getValue(it - 3) }
            paths[adapters.last()]!!
        } else {
            val diffs = adapters.toMutableList().apply { add(0, 0) }.zipWithNext { a, b -> b - a }
            diffs.count { it == 1 } * diffs.count { it == 3 }
        }
    }
}

fun main() {
    println(Adapters().run(part2 = true))
}