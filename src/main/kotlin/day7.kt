import utils.readInputLines

/** [https://adventofcode.com/2020/day/7] */
class Bags : AdventOfCodeTask {

    private val rules = mutableMapOf<String, Set<Pair<String, Int>>>().withDefault { setOf() }

    override fun run(part2: Boolean): Any {
        readInputLines("7.txt").filter { !it.contains("no other bag") }.forEach { rule ->
            val (outer, inners) = rule.split(" bags contain ")
            rules[outer] = inners.split(", ").map { inner ->
                val tokens = inner.split(" ")
                "${tokens[1]} ${tokens[2]}" to tokens[0].toInt()
            }.toSet()
        }

        val color = "shiny gold"
        return if (part2) countBagsIn(color) else countBagsContaining(color)
    }

    private fun countBagsContaining(color: String): Int {
        return rules.keys.filter { findInnerBags(it).contains(color) }.size
    }

    private fun findInnerBags(color: String): Set<String> {
        return rules.getValue(color).fold(setOf()) { acc, (inner, _) ->
            acc + setOf(inner) + findInnerBags(inner)
        }
    }

    private fun countBagsIn(color: String): Int {
        return rules.getValue(color).sumBy { (inner, count) -> count + count * countBagsIn(inner) }
    }
}

fun main() {
    println(Bags().run(part2 = false))
}