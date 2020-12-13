import utils.readInputLines

/** [https://adventofcode.com/2020/day/13] */
class Buses : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val (start, departures) = readInputLines("13.txt")
        val buses = departures.split(",").map { if (it == "x") 1 else it.toLong() }

        return if (part2) {
            val divisors = buses.filter { it != 1L }.toLongArray()
            val remainders = buses.mapIndexedNotNull { index, bus ->
                if (bus == 1L) null else {
                    var mod = bus - index
                    while (mod < 0) {
                        mod += bus
                    }
                    mod
                }
            }.toLongArray()
            chineseRemainder(divisors, remainders)
        } else {
            var current = start.toInt()
            var firstBus: Long? = null
            while (firstBus == null) {
                current++
                firstBus = buses.firstOrNull { it > 1 && current % it == 0L }
            }
            (current - start.toInt()) * firstBus
        }
    }

    // Code for Chinese Remainder Theorem calculation adapted from https://rosettacode.org/wiki/Chinese_remainder_theorem#Kotlin

    private fun chineseRemainder(divisors: LongArray, remainders: LongArray): Long {
        val product = divisors.fold(1L) { acc, i -> acc * i }
        return divisors.zip(remainders).map { (divisor, remainder) ->
            val productOfOthers = product / divisor
            remainder * modularMultiplicativeInverse(productOfOthers, divisor) * productOfOthers
        }.sum() % product
    }

    private fun modularMultiplicativeInverse(a: Long, b: Long): Long {
        if (b == 1L) return 1
        var aa = a
        var bb = b
        var x0 = 0L
        var x1 = 1L
        while (aa > 1) {
            val q = aa / bb
            var t = bb
            bb = aa % bb
            aa = t
            t = x0
            x0 = x1 - q * x0
            x1 = t
        }
        if (x1 < 0) x1 += b
        return x1
    }
}

fun main() {
    print(Buses().run(part2 = true))
}