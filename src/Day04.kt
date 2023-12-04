fun main() {
    fun String.getMatchingNumbers(): Set<String> {
        val (winningNumbersPart, ownNumbersPart) = this.split(": ")[1].split(" | ")
        val winningNumbers = winningNumbersPart.split(" ").filter { it.isNotEmpty() }.toSet()
        val ownNumbers = ownNumbersPart.split(" ").filter { it.isNotEmpty() }.toSet()

        return ownNumbers.intersect(winningNumbers)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val matches = line.getMatchingNumbers()
            val score = matches.fold(0) { acc, _ ->
                if (acc > 0) acc * 2 else 1
            }
            score
        }
    }

    fun part2(input: List<String>): Int {
        return input.foldIndexed(IntArray(input.size) { 1 }) { lineIndex, acc, line ->
            val matches = line.getMatchingNumbers()
            matches.forEachIndexed { matchIndex, _ ->
                if (lineIndex + 1 + matchIndex >= acc.size) return@forEachIndexed
                acc[lineIndex + 1 + matchIndex] += acc[lineIndex]
            }
            acc
        }.sum()
    }

    val testInputPart1 = readInput("Day04_test")
    check(part1(testInputPart1) == 13)

    val testInputPart2 = readInput("Day04_test")
    check(part2(testInputPart2) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
