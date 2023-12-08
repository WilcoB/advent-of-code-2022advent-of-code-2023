fun main() {
    data class MapItem(val sourceRange: LongRange, val destinationRange: LongRange) {
        fun map(input: Long): Long? {
            return if (input in sourceRange) {
                val offset = input - sourceRange.first
                return destinationRange.first + offset
            } else null
        }
    }

    fun List<String>.getMaps(): List<List<MapItem>> {
        val maps: MutableList<List<MapItem>> = mutableListOf()
        var currentMap: MutableList<MapItem> = mutableListOf()

        for (i in this.indices) {
            if (this[i] == "") {
                currentMap = mutableListOf()
                maps.add(currentMap)
            } else if (this[i][0].isDigit()) {
                val (destinationStart, sourceStart, rangeLength) = this[i].split(" ").map { it.toLong() }
                currentMap.add(
                    MapItem(
                        sourceRange = sourceStart..<sourceStart + rangeLength,
                        destinationRange = destinationStart..<destinationStart + rangeLength
                    )
                )
            }
        }

        return maps
    }

    fun List<List<MapItem>>.getLocationId(seed: Long): Long {
        val locationId = fold(seed) { acc, map ->
            map.firstNotNullOfOrNull { mapItem ->
                mapItem.map(acc)
            } ?: acc
        }
        return locationId
    }

    fun part1(input: List<String>): Long {
        val maps = input.getMaps()
        val seeds = input[0].split(": ")[1]
            .split(" ")
            .map { it.toLong() }

        return seeds.minOf { seed ->
            maps.getLocationId(seed)
        }
    }

    fun part2(input: List<String>): Long {
        val maps = input.getMaps()
        val seedRanges = input[0].split(": ")[1]
            .split(" ")
            .map { it.toLong() }
            .chunked(2)
            .map {
                it.first()..<it.first() + it.last()
            }

        // Very slow approach, because every seed from every seed range is checked separately:
        return seedRanges.minOf { range ->
            range.minOfOrNull { seed ->
                maps.getLocationId(seed)
            } ?: Long.MAX_VALUE
        }
    }

    val testInputPart1 = readInput("Day05_test")
    check(part1(testInputPart1) == 35L)

    val testInputPart2 = readInput("Day05_test")
    check(part2(testInputPart2) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
