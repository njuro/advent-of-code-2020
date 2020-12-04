import utils.readInputBlock

/** [https://adventofcode.com/2020/day/4] */
class PassportValidation : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val required = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        val passports = readInputBlock("4.txt").split("\n\n")
            .map { block ->
                block.replace("\n", " ").split(" ")
                    .map { data ->
                        val (key, value) = data.split(":")
                        key to value
                    }.toMap()
            }

        return passports.count { passport ->
            passport.keys.containsAll(required) && passport.all { !part2 || validateData(it.key, it.value) }
        }
    }

    private fun validateData(key: String, value: String): Boolean {
        return try {
            when (key) {
                "byr" -> value.toInt() in 1920..2002
                "iyr" -> value.toInt() in 2010..2020
                "eyr" -> value.toInt() in 2020..2030
                "hgt" -> {
                    val heightPattern = Regex("(\\d+)(cm|in)")
                    val (height, unit) = heightPattern.matchEntire(value)!!.destructured
                    (unit == "cm" && height.toInt() in 150..193) || (unit == "in" && height.toInt() in 59..76)
                }
                "hcl" -> {
                    val colorPattern = Regex("#[0-9a-f]{6}")
                    colorPattern.matches(value)
                }
                "ecl" -> value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                "pid" -> value.length == 9 && value.all { it.isDigit() }
                "cid" -> true
                else -> false
            }
        } catch (_: Exception) {
            false
        }
    }
}

fun main() {
    println(PassportValidation().run(part2 = true))
}