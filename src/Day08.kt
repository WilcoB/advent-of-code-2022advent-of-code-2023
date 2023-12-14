fun main() {
    fun List<String>.getNodes(): Map<String, Pair<String, String>> {
        return drop(2).associate { line ->
            val element = line.substring(0..2)
            val left = line.substring(7..9)
            val right = line.substring(12..14)
            element to Pair(left, right)
        }
    }

    fun Map<String, Pair<String, String>>.countSteps(instructions: String, startElement: String): Long {
        var steps = 0L
        var currentElement = startElement

        while (!currentElement.endsWith("Z")) {
            instructions.forEach { direction ->
                steps ++

                currentElement = when(direction) {
                    'L' -> this[currentElement]!!.first
                    'R' -> this[currentElement]!!.second
                    else -> currentElement
                }

                if (currentElement == "ZZZ")
                    return@forEach
            }
        }

        return steps
    }

    fun part1(input: List<String>): Long {
        val instructions = input[0]
        val nodes = input.getNodes()

        return nodes.countSteps(instructions = instructions, startElement = "AAA")
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0]
        val nodes = input.getNodes()
        val startElements = nodes.map {
            it.key
        }.filter {
            it.endsWith("A")
        }

        return startElements.map {
            nodes.countSteps(instructions = instructions, startElement = it)
        }.reduce { acc, steps ->
            acc.lcm(steps)
        }
    }

    val testInputPart1 = readInput("Day08_test")
    check(part1(testInputPart1) == 6L)

    val testInputPart2 = readInput("Day08_test")
    check(part2(testInputPart2) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
