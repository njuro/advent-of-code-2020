import utils.readInputBlock

/** [https://adventofcode.com/2020/day/6] */
class Answers : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        return readInputBlock("6.txt").split("\n\n").sumBy { group ->
            group.split("\n")
                .map { it.toSet() }.reduce { acc, set -> if (part2) acc.intersect(set) else acc.union(set) }.size
        }
    }
}

fun main() {
    println(Answers().run(part2 = true))
}