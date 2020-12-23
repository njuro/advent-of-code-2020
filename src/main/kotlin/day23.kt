/** [https://adventofcode.com/2020/day/23] */
class Day23 : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        var input = "739862541".toCharArray().map { it.toString().toInt() }.toMutableList()
        var current = input.first()
        var currentIndex = 0
        repeat(100) {
            val moving = listOf(
                input[(currentIndex + 1) % input.size],
                input[(currentIndex + 2) % input.size],
                input[(currentIndex + 3) % input.size]
            )
            input.removeAll(moving)
            var destination = current - 1
            while (destination !in input) {
                destination -= 1
                if (destination < input.minOrNull()!!) {
                    destination = input.maxOrNull()!!
                }
            }
            input.add((input.indexOf(destination) + 1) % input.size, moving[0])
            input.add((input.indexOf(destination) + 2) % input.size, moving[1])
            input.add((input.indexOf(destination) + 3) % input.size, moving[2])

            currentIndex = (input.indexOf(current) + 1) % input.size
            current = input[currentIndex]
        }

        var start = input.indexOf(1)
        var counter = 1
        val result = mutableListOf<Int>()
        while (result.size != input.size - 1) {
            result.add(input[(start + counter) % input.size])
            counter++
        }

        return result.joinToString("")
    }
}

fun main() {
    print(Day23().run(part2 = false))
}