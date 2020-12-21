import utils.readInputLines

/** [https://adventofcode.com/2020/day/21] */
class Ingredients : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val data = readInputLines("21.txt").map {
            val ingredients = it.substring(0, it.indexOf(" (")).split(" ")
            val allergens = it.substring(it.indexOf("(contains ") + 10, it.indexOf(")")).split(", ")
            ingredients to allergens
        }.toMap()

        val candidates = mutableMapOf<String, MutableSet<String>>()
        data.forEach { (ingredients, allergens) ->
            allergens.forEach { allergen ->
                candidates[allergen] =
                    candidates.getOrDefault(allergen, ingredients).intersect(ingredients).toMutableSet()
            }
        }

        if (!part2) {
            val dangerous = candidates.values.flatten().distinct()
            return data.keys.sumBy {
                it.toMutableSet().apply { removeAll(dangerous) }.size
            }
        }

        while (candidates.values.any { it.size > 1 }) {
            candidates.toMutableMap().filterValues { it.size == 1 }.forEach { (allergen, ingredients) ->
                candidates.filterKeys { it != allergen }.values.forEach { it.removeAll(ingredients) }
            }
        }

        return candidates.toSortedMap().values.flatten().distinct().joinToString(",")
    }
}

fun main() {
    print(Ingredients().run(part2 = true))
}