import utils.readInputLines

/** [https://adventofcode.com/2020/day/8] */
class GameLoop : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val instructions = readInputLines("8.txt").map {
            val (op, arg) = it.split(" ")
            op to arg.toInt()
        }

        return if (part2) {
            instructions.forEachIndexed { index, (op, arg) ->
                val updated = instructions.toMutableList()
                if (op == "nop") {
                    updated[index] = "jmp" to arg
                }
                if (op == "jmp") {
                    updated[index] = "nop" to arg
                }
                val result = execute(updated, false)
                if (result != Int.MIN_VALUE) {
                    return result
                }
            }
        } else {
            execute(instructions, true)
        }
    }

    private fun execute(instructions: List<Pair<String, Int>>, loopAllowed: Boolean): Int {
        val visited = mutableSetOf<Int>()
        var accumulator = 0
        var pointer = 0

        while (pointer <= instructions.lastIndex) {
            if (visited.contains(pointer)) {
                return if (loopAllowed) accumulator else Int.MIN_VALUE
            }
            visited.add(pointer)

            val (op, arg) = instructions[pointer]
            when (op) {
                "acc" -> {
                    accumulator += arg
                    pointer++
                }
                "jmp" -> pointer += arg
                "nop" -> pointer++
            }
        }

        return accumulator
    }
}

fun main() {
    println(GameLoop().run(part2 = true))
}