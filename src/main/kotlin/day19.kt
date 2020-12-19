import utils.readInputBlock

/** [https://adventofcode.com/2020/day/19] */
class RegularExpressions : AdventOfCodeTask {

    inner class Definition(val number: Int, val primary: List<Int>, val secondary: List<Int> = listOf()) {
        fun resolvable() = patterns.keys.containsAll(primary + secondary)
    }

    private val definitions = mutableSetOf<Definition>()
    private val patterns = mutableMapOf<Int, String>()

    override fun run(part2: Boolean): Any {
        patterns.clear()

        val (rules, messages) = readInputBlock("19.txt").split("\n\n")
        rules.split("\n").forEach { rule ->
            val (number, definition) = rule.split(": ")
            when {
                '"' in definition -> patterns[number.toInt()] = definition[1].toString()
                '|' in definition -> {
                    val (primary, secondary) = definition.split(" | ")
                    definitions.add(Definition(number.toInt(), primary.parse(), secondary.parse()))
                }
                else -> definitions.add(Definition(number.toInt(), definition.parse()))
            }
        }

        while (definitions.isNotEmpty()) {
            val definition = definitions.firstOrNull(Definition::resolvable) ?: break
            val primaryPattern = definition.primary.resolve()
            val secondaryPattern = definition.secondary.resolve()
            val pattern = "($primaryPattern${if (secondaryPattern.isNotBlank()) "|$secondaryPattern" else ""})"
            patterns[definition.number] = pattern
            definitions.remove(definition)
        }

        if (part2) {
            patterns[8] = "(${patterns[42]})+"
            patterns[11] = "(" + (1..4).joinToString("|") {
                "(${patterns[42]!!.repeat(it)}${patterns[31]!!.repeat(it)})"
            } + ")"
            patterns[0] = "${patterns[8]}${patterns[11]}"
        }

        val regex = patterns[0]!!.toRegex()
        return messages.split("\n").count { regex.matches(it) }
    }

    private fun String.parse() = split(" ").map(String::toInt)
    private fun List<Int>.resolve() = joinToString("", transform = patterns::getValue)
}

fun main() {
    print(RegularExpressions().run(part2 = true))
}