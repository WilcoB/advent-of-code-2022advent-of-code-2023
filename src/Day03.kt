fun main() {

    data class Coordinate(val x: Int, val y: Int)

    data class Part(val number: Int, val startCoordinate: Coordinate, val endCoordinate: Coordinate)

    fun buildGrid(input: List<String>): Array<Array<Char>> {
        return input.foldIndexed(Array(input.size) { Array(input.size) { ' ' } }) { y, acc, row ->
            row.forEachIndexed { x, char ->
                acc[y][x] = char
            }
            acc
        }
    }

    fun getPartsFromGrid(grid: Array<Array<Char>>): List<Part> {
        return grid.foldIndexed(mutableListOf()) { y, acc, row ->
            var startCoordinate: Coordinate? = null
            val currentNumber = StringBuilder()

            row.forEachIndexed { x, item ->
                if (item.isDigit()) {
                    currentNumber.append(item)

                    if (startCoordinate == null) {
                        startCoordinate = Coordinate(x = x, y = y)
                    }

                    // Check if the next coordinate is not outside the grid or a digit, in which case we can add the part to the list:
                    if (x + 1 >= row.size || !row[x + 1].isDigit()) {
                        acc.add(
                            Part(
                                number = currentNumber.toString().toInt(),
                                startCoordinate = startCoordinate!!,
                                endCoordinate = Coordinate(x = x, y = y)
                            )
                        )
                        startCoordinate = null
                        currentNumber.clear()
                    }
                }
            }

            acc
        }
    }

    fun part1(input: List<String>): Int {
        val grid = buildGrid(input = input)
        val parts = getPartsFromGrid(grid = grid)

        val sum = parts.sumOf { part ->
            val materialNumber = part.number
            val startX = part.startCoordinate.x
            val endX = part.endCoordinate.x

            val checkFromX = (startX - 1).coerceAtLeast(0)
            val checkFromY = (part.startCoordinate.y - 1).coerceAtLeast(0)
            val checkToX = (endX + 1).coerceAtMost(grid.size - 1)
            val checkToY = (part.startCoordinate.y + 1).coerceAtMost(grid.size - 1)

            // Check the adjacent characters for symbols, in which case the material number is added to the sum:
            for (y in checkFromY..checkToY) {
                for (x in checkFromX..checkToX) {
                    val character = grid[y][x]
                    if (!character.isDigit() && character != '.') {
                        return@sumOf materialNumber
                    }
                }
            }

            0
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val grid = buildGrid(input = input)
        val parts = getPartsFromGrid(grid = grid)
        val gearCoordinates = grid.foldIndexed(mutableSetOf<Coordinate>()) { y, acc, row ->
            row.forEachIndexed { x, char ->
                if (char == '*') {
                    acc.add(Coordinate(x = x, y = y))
                }
            }
            acc
        }

        val gearRatioSum = gearCoordinates.sumOf { coordinate ->
            val adjacentParts = parts.fold(mutableSetOf<Part>()) { acc, part ->
                val checkFromX = (coordinate.x - 1).coerceAtLeast(0)
                val checkFromY = (coordinate.y - 1).coerceAtLeast(0)
                val checkToX = (coordinate.x + 1).coerceAtMost(grid.size - 1)
                val checkToY = (coordinate.y + 1).coerceAtMost(grid.size - 1)

                // Check for adjacent parts around the gear, in which case it adds to the set:
                if (((part.startCoordinate.x in checkFromX..checkToX && (part.startCoordinate.y in checkFromY..checkToY))
                            || (part.endCoordinate.x in checkFromX..checkToX && (part.endCoordinate.y in checkFromY..checkToY)))
                ) {
                    acc.add(part)
                }

                acc
            }

            val gearRatio = if (adjacentParts.size >= 2) {
                adjacentParts.fold(1) { acc, part ->
                    acc * part.number
                }
            } else 0

            gearRatio
        }

        return gearRatioSum
    }

    val testInputPart1 = readInput("Day03_test")
    check(part1(testInputPart1) == 4361)

    val testInputPart2 = readInput("Day03_test")
    check(part2(testInputPart2) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
