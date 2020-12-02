import utils.readInputLines

/** [https://adventofcode.com/2020/day/2] */
class Passwords : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val pattern = Regex("(\\d+)-(\\d+) (\\w): (\\w+)")
        return readInputLines("2.txt").map { pattern.matchEntire(it) }.count {
            val (min, max, letter, password) = it!!.destructured
            if (part2) {
                val first = password[min.toInt() - 1].toString()
                val second = password[max.toInt() - 1].toString()
                (first == letter) xor (second == letter)
            } else {
                val count = password.count { c -> c == letter[0] }
                count >= min.toInt() && count <= max.toInt()
            }
        }
    }
}

fun main() {
    println(Passwords().run(part2 = true))
}