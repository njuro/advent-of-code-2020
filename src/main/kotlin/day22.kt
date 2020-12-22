import utils.readInputBlock

/** [https://adventofcode.com/2020/day/22] */
class CardGame : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val (player1, player2) = readInputBlock("22.txt").split("\n\n")
        return play(player1.parseDeck(), player2.parseDeck(), recursive = part2).second
    }

    private fun play(player1: MutableList<Int>, player2: MutableList<Int>, recursive: Boolean): Pair<Int, Int> {
        val seen = mutableSetOf<Pair<List<Int>, List<Int>>>()
        var winner = 1

        while (player1.isNotEmpty() && player2.isNotEmpty()) {
            if (seen.contains(player1 to player2)) {
                return 1 to -1
            }
            
            seen.add(player1.toList() to player2.toList())
            val player1card = player1.removeFirst()
            val player2card = player2.removeFirst()
            winner = if (recursive && player1.size >= player1card && player2.size >= player2card) {
                play(
                    player1.toMutableList().subList(0, player1card),
                    player2.toMutableList().subList(0, player2card),
                    recursive
                ).first
            } else {
                if (player1card > player2card) 1 else 2
            }

            if (winner == 1) {
                player1.add(player1card)
                player1.add(player2card)
            } else {
                player2.add(player2card)
                player2.add(player1card)
            }
        }

        val score = if (winner == 2) player2.calculateScore() else player1.calculateScore()
        return winner to score
    }

    private fun String.parseDeck() = split("\n").drop(1).map(String::toInt).toMutableList()
    private fun List<Int>.calculateScore() = mapIndexed { index, value -> value * (size - index) }.sum()
}

fun main() {
    print(CardGame().run(part2 = true))
}