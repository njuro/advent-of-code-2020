import utils.readInputBlock

/** [https://adventofcode.com/2020/day/23] */
class Cups : AdventOfCodeTask {

    data class Node(val value: Int) {
        lateinit var next: Node
    }

    override fun run(part2: Boolean): Any {
        val input = readInputBlock("23.txt").map(Character::getNumericValue)
        val startNode = Node(input.first())
        val nodeMap = mutableMapOf(input.first() to startNode)
        var currentNode = startNode
        (input.drop(1) + if (part2) 10..1_000_000 else listOf()).forEach {
            val node = Node(it)
            nodeMap[it] = node
            currentNode.next = node
            currentNode = node
        }
        currentNode.next = startNode
        currentNode = startNode

        repeat(if (part2) 10_000_000 else 100) {
            val selectedStart = currentNode.next
            val selectedMiddle = currentNode.next.next
            val selectedEnd = currentNode.next.next.next
            val selectedValues = setOf(selectedStart, selectedMiddle, selectedEnd).map(Node::value)
            currentNode.next = selectedEnd.next

            var destinationValue = currentNode.value
            do {
                destinationValue--
                if (destinationValue == 0) {
                    destinationValue = if (part2) 1_000_000 else 9
                }
            } while (destinationValue in selectedValues)
            val destinationNode = nodeMap.getValue(destinationValue)

            val destinationNodeNext = destinationNode.next
            destinationNode.next = selectedStart
            selectedEnd.next = destinationNodeNext
            currentNode = currentNode.next
        }

        val cupOne = nodeMap.getValue(1)
        return if (part2) {
            cupOne.next.value.toLong() * cupOne.next.next.value
        } else {
            val result = mutableListOf<Int>()
            var current = cupOne.next
            while (current.value != 1) {
                result.add(current.value)
                current = current.next
            }
            result.joinToString("").toInt()
        }
    }
}

fun main() {
    print(Cups().run(part2 = false))
}