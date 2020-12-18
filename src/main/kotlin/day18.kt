import utils.readInputLines

/** [https://adventofcode.com/2020/day/18] */
class Equations : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        return readInputLines("18.txt")
            .map { it.replace(" ", "") }
            .map { expression -> evaluate(expression, additionPrecedence = part2) }
            .sum()
    }

    private fun evaluate(expression: String, additionPrecedence: Boolean): Long {
        var result = 0L
        var index = 0
        var operator: Char? = null
        val multipliers = mutableListOf<Long>()

        fun applyOperator(value: Long) = when (operator) {
            '+' -> result += value
            '*' -> if (additionPrecedence) {
                multipliers.add(result)
                result = value
            } else result *= value
            else -> result = value
        }

        while (index < expression.length) {
            if (expression[index] == '(') {
                var nestingLevel = 0
                var endIndex = index + 1
                while (true) {
                    if (expression[endIndex] == '(') {
                        nestingLevel++
                    } else if (expression[endIndex] == ')') {
                        if (nestingLevel == 0) break else nestingLevel--
                    }
                    endIndex++
                }

                val subExpression = evaluate(expression.substring(index + 1, endIndex), additionPrecedence)
                applyOperator(subExpression)

                index = endIndex + 1
                continue
            }

            if (Character.isDigit(expression[index])) {
                val digit = Character.getNumericValue(expression[index]).toLong()
                applyOperator(digit)

                index++
                continue
            }

            operator = expression[index]
            index++
        }

        return if (additionPrecedence) {
            (multipliers + result).reduce { a, b -> a * b }
        } else result
    }
}

fun main() {
    print(Equations().run(part2 = true))
}