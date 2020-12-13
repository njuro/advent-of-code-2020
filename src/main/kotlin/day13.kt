import utils.readInputLines

/** [https://adventofcode.com/2020/day/13] */
class Buses : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val (start, departures) = readInputLines("13.txt")
        val buses = departures.split(",").map { if (it == "x") 1 else it.toLong() }

        return if (part2) {
            val dividers = buses.filter { it != 1L }.toLongArray()
            val modulos = buses.mapIndexedNotNull { index, bus ->
                if (bus == 1L) null else {
                    var mod = bus - index
                    while (mod < 0) {
                        mod += bus
                    }
                    mod
                }
            }.toLongArray()
            chineseRemainder(dividers, modulos)
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

    // Code for Chinese Remainder Theorem calculation taken from https://rosettacode.org/wiki/Chinese_remainder_theorem#Kotlin
    // Adapted from Ints to Longs to prevent overflow

    private fun chineseRemainder(n: LongArray, a: LongArray): Long {
        val prod = n.fold(1L) { acc, i -> acc * i }
        var sum = 0L
        for (i in n.indices) {
            val p = prod / n[i]
            sum += a[i] * multInv(p, n[i]) * p
        }
        return sum % prod
    }

    private fun multInv(a: Long, b: Long): Long {
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