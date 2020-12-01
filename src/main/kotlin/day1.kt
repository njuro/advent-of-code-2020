import utils.readInputLines

/** [https://adventofcode.com/2020/day/1] */
class FindSum : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val values = readInputLines("1.txt").map { it.toInt() }
        for (i in values) {
            for (j in values) {
                if (part2) {
                    for (k in values) {
                        if (i + j + k == 2020) {
                            return i * j * k
                        }
                    }
                } else {
                    if (i + j == 2020) {
                        return i * j
                    }
                }
            }
        }

        throw IllegalStateException()
    }
}

fun main() {
    println(FindSum().run(part2 = true))
}