fun main() {
    fun part1(input: List<String>): Int {
        val instructions = input[0]
        val nodes = input.drop(2).associate { line ->
            val element = line.substring(0..2)
            val left = line.substring(7..9)
            val right = line.substring(12..14)
            element to Pair(left, right)
        }

        var steps = 0
        var currentElement = "AAA"

        while (currentElement != "ZZZ") {
            instructions.forEach { direction ->
                steps ++

                currentElement = when(direction) {
                    'L' -> nodes[currentElement]!!.first
                    'R' -> nodes[currentElement]!!.second
                    else -> currentElement
                }

                if (currentElement == "ZZZ")
                    return@forEach
            }
        }

        return steps
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInputPart1 = readInput("Day08_test")
    check(part1(testInputPart1) == 6)

    val testInputPart2 = readInput("Day08_test")
    check(part2(testInputPart2) == 0)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
