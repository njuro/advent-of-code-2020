import utils.readInputLines

/** [https://adventofcode.com/2020/day/17] */
class Cubes : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val dimensions = if (part2) 4 else 3
        val filler = generateSequence { 0 }.take(dimensions - 2).toList()
        var active = readInputLines("17.txt")
            .flatMapIndexed { y, row ->
                row.mapIndexedNotNull { x, c ->
                    if (c == '#') {
                        listOf(x, y) + filler
                    } else null
                }
            }.toMutableSet()

        val mask = generateMask(dimensions).apply { removeIf { list -> list.all { it == 0 } } }
        repeat(6) {
            val updated = active.toMutableSet()
            val inactiveNeighbours = mutableMapOf<List<Int>, Int>().withDefault { 0 }
            active.forEach { coordinate ->
                val neighbours = mask.map { offsets -> offsets.zip(coordinate).map { (a, b) -> a + b } }
                neighbours.filter { it !in active }
                    .forEach { inactiveNeighbours[it] = inactiveNeighbours.getValue(it) + 1 }
                val count = neighbours.count { it in active }
                if (count < 2 || count > 3) {
                    updated.remove(coordinate)
                }
            }
            updated.addAll(inactiveNeighbours.filterValues { it == 3 }.keys)
            active = updated
        }

        return active.size
    }

    private fun generateMask(length: Int): MutableSet<List<Int>> {
        return if (length <= 0) {
            mutableSetOf(listOf())
        } else {
            val result = mutableSetOf<List<Int>>()
            generateMask(length - 1).forEach { list ->
                result.add(list + 0)
                result.add(list + 1)
                result.add(list + -1)
            }
            result
        }
    }
}

fun main() {
    print(Cubes().run(part2 = true))
}