fun main() {
    fun String.getDiffSequences(): List<List<Long>> {
        val dataset = split(" ").map { it.toLong() }
        val sequences = mutableListOf(dataset)

        while (sequences.last().any { it != 0L }) {
            val currentSequence = sequences.last()
            val newSequence = mutableListOf<Long>()
            var i = 0

            while (i in currentSequence.indices && i != currentSequence.lastIndex) {
                newSequence.add(currentSequence[i + 1] - currentSequence[i])
                i++
            }

            sequences.add(newSequence)
        }

        return sequences
    }

    fun part1(input: List<String>): Long {
        return input.map { it.getDiffSequences() }
            .sumOf { diffSequences ->
                diffSequences.sumOf { it.last() }
            }
    }

    fun part2(input: List<String>): Long {
        return input.map { it.getDiffSequences() }
            .sumOf { diffSequences ->
                diffSequences.reversed().fold(0L) { acc, sequence ->
                    sequence.first() - acc
                }
            }
    }

    val testInputPart1 = readInput("Day09_test")
    check(part1(testInputPart1) == 114L)

    val testInputPart2 = readInput("Day09_test")
    check(part2(testInputPart2) == 2L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
