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

    private fun runTaskTest(task: AdventOfCodeTask, part1Result: Any, part2Result: Any) {
        assertEquals(part1Result, task.run())
        assertEquals(part2Result, task.run(part2 = true))
    }
}