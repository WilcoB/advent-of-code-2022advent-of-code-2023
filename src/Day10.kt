data class Coordinates(val x: Int, val y: Int) {
    fun equals(other: Coordinates) = this.x == other.x && this.y == other.y
}

data class Tile(val type: TileType, val coordinates: Coordinates)

enum class TileType(
    val symbol: Char,
    val possibleLeft: List<Char> = emptyList(),
    val possibleRight: List<Char> = emptyList(),
    val possibleTop: List<Char> = emptyList(),
    val possibleBottom: List<Char> = emptyList()
) {
    VERTICAL_PIPE(symbol = '|', possibleTop = listOf('|', '7', 'F'), possibleBottom = listOf('|', 'L', 'J')),
    HORIZONTAL_PIPE(symbol = '-', possibleLeft = listOf('-', 'L', 'F'), possibleRight = listOf('-', 'J', '7')),
    NORTH_EAST_BEND(symbol = 'L', possibleTop = listOf('|', '7', 'F'), possibleRight = listOf('-', '7', 'J')),
    NORTH_WEST_BEND(symbol = 'J', possibleTop = listOf('|', '7', 'F'), possibleLeft = listOf('-', 'L', 'F')),
    SOUTH_WEST_BEND(symbol = '7', possibleLeft = listOf('-', 'L', 'F'), possibleBottom = listOf('|', 'L', 'J')),
    SOUTH_EAST_BEND(symbol = 'F', possibleRight = listOf('-', 'J', '7'), possibleBottom = listOf('|', 'L', 'J')),
    GROUND(symbol = '.'),
    START(symbol = 'S');

    companion object {
        infix fun from(symbol: Char): TileType = TileType.values().first { it.symbol == symbol }
    }
}

fun main() {
    fun List<String>.getStartCoordinates(): Coordinates? {
        forEachIndexed { y, line ->
            line.forEachIndexed { x, tile ->
                if (tile == 'S') {
                    return Coordinates(x = x, y = y)
                }
            }
        }

        return null
    }

    fun List<String>.getTile(coordinates: Coordinates): Tile? {
        val symbol = if ((coordinates.x >= 0 && coordinates.x < this[0].length)
            && (coordinates.y >= 0 && coordinates.y < this.size)
        ) {
            this[coordinates.y][coordinates.x]
        } else null

        return symbol?.let { Tile(type = TileType from it, coordinates = coordinates) }
    }


    fun List<String>.determineStartTile(): Tile {
        val startCoordinates = getStartCoordinates()!!
        val left = getTile(coordinates = Coordinates(x = startCoordinates.x - 1, y = startCoordinates.y))
        val right = getTile(coordinates = Coordinates(x = startCoordinates.x + 1, y = startCoordinates.y))
        val top = getTile(coordinates = Coordinates(x = startCoordinates.x, y = startCoordinates.y - 1))
        val bottom = getTile(coordinates = Coordinates(x = startCoordinates.x, y = startCoordinates.y + 1))

        val possibleFromLeftTile = left?.type?.possibleRight ?: emptyList()
        val possibleFromRightTile = right?.type?.possibleLeft ?: emptyList()
        val possibleFromTopTile = top?.type?.possibleBottom ?: emptyList()
        val possibleFromBottomTime = bottom?.type?.possibleTop ?: emptyList()

        val combined = possibleFromLeftTile + possibleFromRightTile + possibleFromTopTile + possibleFromBottomTime
        val startSymbol = combined.groupBy { it }
            .filter { it.value.size == 2 }.keys
            .first()

        return Tile(type = TileType from startSymbol, coordinates = startCoordinates)
    }

    fun List<String>.getConnectingTile(previous: Tile?, current: Tile): Tile? {
        val left = getTile(Coordinates(x = current.coordinates.x - 1, y = current.coordinates.y))
        val right = getTile(Coordinates(x = current.coordinates.x + 1, y = current.coordinates.y))
        val top = getTile(Coordinates(x = current.coordinates.x, y = current.coordinates.y - 1))
        val bottom = getTile(Coordinates(x = current.coordinates.x, y = current.coordinates.y + 1))

        return when {
            left != previous && current.type.possibleLeft.contains(left?.type?.symbol) -> left!!
            right != previous && current.type.possibleRight.contains(right?.type?.symbol) -> right!!
            top != previous && current.type.possibleTop.contains(top?.type?.symbol) -> top!!
            bottom != previous && current.type.possibleBottom.contains(bottom?.type?.symbol) -> bottom!!
            else -> null
        }
    }

    fun List<String>.getLoopTiles(): List<Tile> {
        val startTile = determineStartTile()
        val loopTiles = mutableListOf(startTile)
        var previous: Tile? = null
        var endOfLoop = false

        while (!endOfLoop) {
            val current = loopTiles.lastOrNull() ?: startTile
            val connectingTile = getConnectingTile(previous = previous, current = current)
            if (connectingTile != null) {
                loopTiles.add(connectingTile)
            } else {
                endOfLoop = true
            }

            previous = current
        }

        return loopTiles
    }

    fun part1(input: List<String>): Int {
        return input.getLoopTiles().size / 2
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInputPart1 = readInput("Day10_test1")
    check(part1(testInputPart1) == 8)

    val testInputPart2 = readInput("Day10_test2")
    check(part2(testInputPart2) == 0)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
