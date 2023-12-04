fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val gameId = line.split(":")[0].split(" ")[1]
            val sets = line.split(":")[1].split(";")

            sets.forEach { set ->
                val subsets = set.split(",")

                subsets.forEach { subset ->
                    val (amount, color) = subset.trim().split(" ")
                    val isPossible = when (color) {
                        "red" -> amount.toInt() <= 12
                        "green" -> amount.toInt() <= 13
                        "blue" -> amount.toInt() <= 14
                        else -> true
                    }

                    if (!isPossible) {
                        return@sumOf 0
                    }
                }
            }

            gameId.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val sets = line.split(":")[1].split(";")
            val maxColorMap = mutableMapOf<String, Int>()

            sets.forEach { set ->
                val subsets = set.split(",")

                subsets.forEach { subset ->
                    val (amount, color) = subset.trim().split(" ")
                    val currentAmount = maxColorMap.getOrDefault(color, 0)

                    if (currentAmount < amount.toInt()) {
                        maxColorMap[color] = amount.toInt()
                    }
                }
            }

            val red = maxColorMap.getOrDefault("red", 0)
            val green = maxColorMap.getOrDefault("green", 0)
            val blue = maxColorMap.getOrDefault("blue", 0)

            red * green * blue
        }
    }

    val testInputPart1 = readInput("Day02_test1")
    check(part1(testInputPart1) == 8)

    val testInputPart2 = readInput("Day02_test2")
    check(part2(testInputPart2) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
