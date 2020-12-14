import utils.readInputLines

/** [https://adventofcode.com/2020/day/14] */
class BinaryStrings : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val memory = mutableMapOf<Long, Long>().withDefault { 0L }
        var mask = ""

        readInputLines("14.txt").forEach {
            val (command, value) = it.split(" = ")
            if (command == "mask") {
                mask = value
            } else {
                val (address) = Regex("mem\\[(\\d+)]").matchEntire(command)!!.destructured
                val binary = if (part2) address.toBinaryList() else value.toBinaryList()
                mask.forEachIndexed { index, bit ->
                    binary[index] = when {
                        bit == '0' && !part2 -> '0'
                        bit == '1' -> '1'
                        bit == 'X' && part2 -> 'X'
                        else -> binary[index]
                    }
                }
                if (part2) {
                    val replacements = generateBinaryStrings(binary.count { bit -> bit == 'X' })
                    replacements.map(CharSequence::iterator).forEach { replacement ->
                        val updated = binary.toMutableList()
                        binary.forEachIndexed { index, bit -> if (bit == 'X') updated[index] = replacement.nextChar() }
                        memory[updated.toLong()] = value.toLong()
                    }
                } else {
                    memory[address.toLong()] = binary.toLong()
                }
            }
        }

        return memory.values.sum()
    }

    private fun generateBinaryStrings(length: Int, prefix: String = ""): Set<String> {
        if (prefix.length == length) {
            return setOf(prefix)
        }

        return mutableSetOf<String>().apply {
            addAll(generateBinaryStrings(length, prefix + "0"))
            addAll(generateBinaryStrings(length, prefix + "1"))
        }
    }

    private fun String.toBinaryList() = Integer.toBinaryString(this.toInt()).padStart(36, '0').toMutableList()
    private fun List<Char>.toLong() = joinToString("").toLong(2)
}

fun main() {
    print(BinaryStrings().run(part2 = true))
}