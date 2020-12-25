import utils.readInputLines

/** [https://adventofcode.com/2020/day/25] */
class UnlockCode : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val (cardPublicKey, doorPublicKey) = readInputLines("25.txt").map(String::toLong)
        val cardLoopSize = generate(7).first { it.second == cardPublicKey }.first
        return generate(doorPublicKey).drop(cardLoopSize).first().second
    }

    private fun generate(subject: Long) =
        generateSequence(0 to 1L) { (index, value) -> index + 1 to (value * subject) % 20201227 }
}

fun main() {
    print(UnlockCode().run(part2 = false))
}