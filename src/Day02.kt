fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            val gameId = line.split(":")[0].split(" ")[1]
            val sets = line.split(":")[1].split(";")

            sets.forEach { set ->
                val subsets = set.split(",")

                subsets.forEach { subset ->
                    val (amount, color) = subset.trim().split(" ")
                    val isPossible = when(color) {
                        "red" -> amount.toInt() <= 12
                        "green" ->  amount.toInt() <= 13
                        "blue" -> amount.toInt() <= 14
                        else -> true
                    }

                    if (!isPossible) {
                        return@fold acc
                    }
                }
            }

            acc + gameId.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInputPart1 = readInput("Day02_test1")
    check(part1(testInputPart1) == 8)

    val testInputPart2 = readInput("Day02_test2")
    check(part2(testInputPart2) == 1)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
