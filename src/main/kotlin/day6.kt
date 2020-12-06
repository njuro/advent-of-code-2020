import utils.readInputBlock

/** [https://adventofcode.com/2020/day/6] */
class Answers : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val groups = readInputBlock("6.txt").split("\n\n")
        val answers = mutableMapOf<Char, Int>().withDefault { 0 }
        var counter = 0
        for (group in groups) {
            val responses = group.split("\n")
            for (response in responses) {
                for (answer in response) {
                    answers[answer] = answers.getValue(answer) + 1
                }
            }
            counter += if (part2) answers.filterValues { it == responses.size }.size else answers.size
            answers.clear()
        }

        return counter
    }
}

fun main() {
    println(Answers().run(part2 = true))
}