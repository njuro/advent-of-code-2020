import utils.readInputBlock

/** [https://adventofcode.com/2020/day/16] */
class Tickets : AdventOfCodeTask {

    data class Field(val name: String, val range: IntRange, val exceptions: IntRange) {
        fun isAllowed(value: Int) = value in range && value !in exceptions
    }

    override fun run(part2: Boolean): Any {
        val (definitions, your, nearby) = readInputBlock("16.txt").split("\n\n")
        val definitionPattern = Regex("(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)")

        val fields = definitions.split("\n").map {
            val (name, start1, end1, start2, end2) = definitionPattern.matchEntire(it)!!.destructured
            val allowed = start1.toInt()..end2.toInt()
            val exceptions = (end1.toInt() + 1) until start2.toInt()
            Field(name, allowed, exceptions)
        }

        var errorRate = 0
        val tickets = parseTickets(nearby).filter { ticket ->
            ticket.all { value ->
                val valid = fields.any { field -> field.isAllowed(value) }
                if (!valid) errorRate += value
                valid
            }
        }

        if (!part2) return errorRate

        val candidates = fields.indices.map { index ->
            index to fields.filter { field ->
                tickets.all { ticket -> field.isAllowed(ticket[index]) }
            }.map(Field::name).toMutableList()
        }.sortedBy { (_, fields) -> fields.size }

        val valid = candidates.map { (index, fields) ->
            val field = fields.first()
            candidates.forEach { (_, f) -> f.remove(field) }
            index to field
        }.toMap()

        val yourTicket = parseTickets(your).first()
        return valid.filterValues { it.startsWith("departure") }
            .keys.fold(1L) { product, index -> product * yourTicket[index] }
    }

    private fun parseTickets(tickets: String) = tickets.split("\n").drop(1).map { ticket ->
        ticket.split(",").map { it.toInt() }
    }
}

fun main() {
    print(Tickets().run(part2 = true))
}