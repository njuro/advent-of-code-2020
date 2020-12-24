import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AdventOfCodeTasksTest {

    @Test
    fun day1() {
        runTaskTest(FindSum(), 996996, 9210402)
    }

    @Test
    fun day2() {
        runTaskTest(Passwords(), 582, 729)
    }

    @Test
    fun day3() {
        runTaskTest(TreeSlopes(), 276L, 7812180000L)
    }

    @Test
    fun day4() {
        runTaskTest(PassportValidation(), 230, 156)
    }

    @Test
    fun day5() {
        runTaskTest(AirplaneSeats(), 911, 629)
    }

    @Test
    fun day6() {
        runTaskTest(Answers(), 6542, 3299)
    }

    @Test
    fun day7() {
        runTaskTest(Bags(), 164, 7872)
    }

    @Test
    fun day8() {
        runTaskTest(GameLoop(), 1801, 2060)
    }

    @Test
    fun day9() {
        runTaskTest(FindSumYetAgain(), 257342611L, 35602097L)
    }

    @Test
    fun day10() {
        runTaskTest(Adapters(), 2414, 21156911906816L)
    }

    @Test
    fun day11() {
        runTaskTest(FerrySeats(), 2249, 2023)
    }

    @Test
    fun day12() {
        runTaskTest(Navigation(), 1565, 78883)
    }

    @Test
    fun day13() {
        runTaskTest(Buses(), 1895L, 840493039281088L)
    }

    @Test
    fun day14() {
        runTaskTest(BinaryStrings(), 11612740949946L, 3394509207186L)
    }

    @Test
    fun day15() {
        runTaskTest(MemoryGame(), 536, 24065124)
    }

    @Test
    fun day16() {
        runTaskTest(Tickets(), 20058, 366871907221L)
    }

    @Test
    fun day17() {
        runTaskTest(Cubes(), 223, 1884)
    }

    @Test
    fun day18() {
        runTaskTest(Equations(), 86311597203806L, 276894767062189L)
    }

    @Test
    fun day19() {
        runTaskTest(RegularExpressions(), 285, 412)
    }

    @Test
    fun day20() {
        runTaskTest(Tiles(), 8581320593371L, 2031)
    }

    @Test
    fun day21() {
        runTaskTest(Ingredients(), 2556, "vcckp,hjz,nhvprqb,jhtfzk,mgkhhc,qbgbmc,bzcrknb,zmh")
    }

    @Test
    fun day22() {
        runTaskTest(CardGame(), 31809, 32835)
    }

    @Test
    fun day23() {
        runTaskTest(Cups(), 94238657, 3072905352L)
    }

    @Test
    fun day24() {
        runTaskTest(HexGrid(), 360, 3924)
    }

    private fun runTaskTest(task: AdventOfCodeTask, part1Result: Any, part2Result: Any) {
        assertEquals(part1Result, task.run())
        assertEquals(part2Result, task.run(part2 = true))
    }
}