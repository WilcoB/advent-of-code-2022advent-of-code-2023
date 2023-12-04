fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line.filter { it.isDigit() }.let {
                "${it.first()}${it.last()}".toInt()
            }
        }
    }

    fun part2(input: List<String>): Int {
        val digitMap = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )

        return input.sumOf { line ->
            val digits = line.foldIndexed("") { index, digitAcc, c ->
                if (c.isDigit()) {
                    return@foldIndexed digitAcc + c
                } else {
                    digitMap.forEach {
                        if (line.substring(index).startsWith(it.key)) {
                            return@foldIndexed digitAcc + it.value
                        }
                    }
                }

                digitAcc
            }

            digits.let {
                "${it.first()}${it.last()}".toInt()
            }
        }
    }

    val testInputPart1 = readInput("Day01_test1")
    check(part1(testInputPart1) == 142)

    val testInputPart2 = readInput("Day01_test2")
    check(part2(testInputPart2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
