import kotlin.math.abs

fun main() {
    fun List<String>.getShortestPathSum(expansionTimes: Int): Long {
        val emptyRows = this.foldIndexed(mutableSetOf<Int>()) { y, emptyRows, row ->
            if (row.all { it == '.' }) {
                emptyRows.add(y)
            }
            emptyRows
        }

        val emptyColumns = this[0].foldIndexed(mutableSetOf<Int>()) { x, emptyColumns, _ ->
            if (this.all { this[this.indexOf(it)][x] == '.' }) {
                emptyColumns.add(x)
            }
            emptyColumns
        }

        val galaxies = this.foldIndexed(mutableListOf<Pair<Int, Int>>()) { y, galaxies, _ ->
            for (x in 0..< this[0].length) {
                if (this[y][x] == '#') {
                    val extraColumns = emptyColumns.count { it < x } * (expansionTimes - 1).coerceAtLeast(1)
                    val extraRows = emptyRows.count { it < y } * (expansionTimes - 1).coerceAtLeast(1)
                    galaxies.add(x + extraColumns to y + extraRows)
                }
            }
            galaxies
        }

        return galaxies.sumOf { firstGalaxy ->
            val galaxyIndex = galaxies.indexOf(firstGalaxy)
            val remainingGalaxies = galaxies.subList(galaxyIndex, galaxies.size)

            remainingGalaxies.sumOf { secondGalaxy ->
                val (firstX, firstY) = firstGalaxy
                val (secondX, secondY) = secondGalaxy
                abs(secondX - firstX) + abs(secondY - firstY).toLong()
            }
        }
    }

    fun part1(input: List<String>): Long {
        return input.getShortestPathSum(expansionTimes = 1)
    }

    fun part2(input: List<String>): Long {
        return input.getShortestPathSum(expansionTimes = 1_000_000)
    }

    val testInputPart1 = readInput("Day11_test")
    check(part1(testInputPart1) == 374L)

    val testInputPart2 = readInput("Day11_test")
    check(part2(testInputPart2) == 82000210L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}