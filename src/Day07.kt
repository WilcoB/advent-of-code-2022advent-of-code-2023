fun main() {
    val possibleCards = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

    fun String.hasNumberOfTheSameCard(number: Int): Boolean {
        return this.groupBy { it.toString() }.any {
            it.value.size == number
        }
    }

    fun String.isFullHouse(): Boolean {
        val groupedCards = this.groupBy { it.toString() }
        return groupedCards.any {
            it.value.size == 3
        } && groupedCards.any {
            it.value.size == 2
        }
    }

    fun String.hasNumberOfPairs(amount: Int): Boolean {
        return this.groupBy { it.toString() }.count {
            it.value.size == 2
        } == amount
    }

    fun Char.getCardValue(): Int {
        return possibleCards.reversed().indexOf(this)
    }

    fun part1(input: List<String>): Int {
        val handsWithBids = input.map {
            val (hand, bid) = it.split(" ")
            hand to bid.toInt()
        }

        val sortedHands = handsWithBids.sortedWith(compareBy(
            {
                // We first compare based on the hand type, where the better hands get a higher strength:
                val hand = it.first
                val strength = when {
                    hand.hasNumberOfTheSameCard(number = 5) -> 7
                    hand.hasNumberOfTheSameCard(number = 4) -> 6
                    hand.isFullHouse() -> 5
                    hand.hasNumberOfTheSameCard(number = 3) -> 4
                    hand.hasNumberOfPairs(amount = 2) -> 3
                    hand.hasNumberOfPairs(amount = 1) -> 2
                    else -> { 1 }
                }
                strength
            },
            // In case the hand type is the same, the second ordering rule takes effect:
            { it.first[0].getCardValue() },
            { it.first[1].getCardValue() },
            { it.first[2].getCardValue() },
            { it.first[3].getCardValue() },
            { it.first[4].getCardValue() })
        )

        val totalWinnings = sortedHands.sumOf { handWithBid ->
            val rank = sortedHands.indexOf(handWithBid) + 1
            val bid = handWithBid.second

            rank * bid
        }

        return totalWinnings
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInputPart1 = readInput("Day07_test")
    check(part1(testInputPart1) == 6440)

    val testInputPart2 = readInput("Day07_test")
    check(part2(testInputPart2) == 0)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
