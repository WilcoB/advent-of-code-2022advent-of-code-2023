fun main() {
    data class Race(val time: Long, val currentRecord: Long) {
        fun countWins(): Int {
            return (1..time).count { speed ->
                val timeRemaining = time - speed
                val distance = speed * timeRemaining

                distance > currentRecord
            }
        }
    }

    fun List<String>.getDataFromLine(index: Int): List<Long> {
        return this[index].substringAfter(":")
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toLong() }
    }

    fun List<Long>.mergeNumbers(): Long {
        return fold(StringBuilder()) { acc, digits ->
            acc.append(digits)
        }.toString().toLong()
    }

    fun List<String>.getMultipleRaces(): List<Race> {
        val times = getDataFromLine(index = 0)
        val distances = getDataFromLine(index = 1)

        return times.mapIndexed { index, time ->
            Race(time = time, currentRecord = distances[index])
        }
    }

    fun List<String>.getSingleRace(): Race {
        return Race(
            time = getDataFromLine(index = 0).mergeNumbers(),
            currentRecord = getDataFromLine(index = 1).mergeNumbers()
        )
    }

    fun part1(input: List<String>): Int {
        return input.getMultipleRaces().fold(1) { acc, race ->
            acc * race.countWins()
        }
    }

    fun part2(input: List<String>): Int {
        return input.getSingleRace().countWins()
    }

    val testInputPart1 = readInput("Day06_test")
    check(part1(testInputPart1) == 288)

    val testInputPart2 = readInput("Day06_test")
    check(part2(testInputPart2) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
