fun main() {
    val cardOrderPart1 = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    val cardOrderPart2 = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

    fun String.getJokerCount(): Int {
        return count { it == 'J' }
    }

    fun String.hasNumberOfTheSameCard(number: Int, jokerUsage: Boolean): Boolean {
        return groupBy { it }.any {
            val size = if (jokerUsage && it.key != 'J') {
                it.value.size + getJokerCount()
            } else {
                it.value.size
            }

            size == number
        }
    }

    fun String.hasNumberOfPairs(numberOfPairs: Int, jokerUsage: Boolean): Boolean {
        val groupedCards = groupBy { it }
        val jokers = getJokerCount()

        val normalCheck = groupedCards.size <= (5 - numberOfPairs)
        val jokerCheck = (jokers >= numberOfPairs) || (numberOfPairs == 2 && jokers == 1 && groupedCards.size <= 4)

        return normalCheck || (jokerUsage && jokerCheck)
    }

    fun String.isFullHouse(jokerUsage: Boolean): Boolean {
        val groupedCards = groupBy { it }
        val jokers = getJokerCount()

        val normalCheck = groupedCards.any {
            it.value.size == 3
        } && groupedCards.any {
            it.value.size == 2
        }
        val jokerCheck = jokers >= 1 && groupedCards.size <= 3

        return normalCheck || (jokerUsage && jokerCheck)
    }

    fun Char.getCardValue(jokerUsage: Boolean): Int {
        return if (jokerUsage) {
            cardOrderPart2.indexOf(this)
        } else {
            cardOrderPart1.indexOf(this)
        }
    }

    fun List<String>.sortAndGetWinnings(jokerUsage: Boolean): Int {
        val handsWithBids = map {
            val (hand, bid) = it.split(" ")
            hand to bid.toInt()
        }

        val sortedHands = handsWithBids.sortedWith(
            compareBy(
                {
                    // We first compare based on the hand type, where the better hands get a higher strength:
                    val hand = it.first
                    val strength = when {
                        hand.hasNumberOfTheSameCard(number = 5, jokerUsage = jokerUsage) -> 7
                        hand.hasNumberOfTheSameCard(number = 4, jokerUsage = jokerUsage) -> 6
                        hand.isFullHouse(jokerUsage = jokerUsage) -> 5
                        hand.hasNumberOfTheSameCard(number = 3, jokerUsage = jokerUsage) -> 4
                        hand.hasNumberOfPairs(numberOfPairs = 2, jokerUsage = jokerUsage) -> 3
                        hand.hasNumberOfPairs(numberOfPairs = 1, jokerUsage = jokerUsage) -> 2
                        else -> {
                            1
                        }
                    }
                    strength
                },
                // In case the hand type is the same, the second ordering rule takes effect:
                { it.first[0].getCardValue(jokerUsage = jokerUsage) },
                { it.first[1].getCardValue(jokerUsage = jokerUsage) },
                { it.first[2].getCardValue(jokerUsage = jokerUsage) },
                { it.first[3].getCardValue(jokerUsage = jokerUsage) },
                { it.first[4].getCardValue(jokerUsage = jokerUsage) })
        )

        return sortedHands.sumOf { handWithBid ->
            val rank = sortedHands.indexOf(handWithBid) + 1
            val bid = handWithBid.second

            rank * bid
        }
    }

    fun part1(input: List<String>): Int {
        return input.sortAndGetWinnings(jokerUsage = false)
    }

    fun part2(input: List<String>): Int {
        return input.sortAndGetWinnings(jokerUsage = true)
    }

    val testInputPart1 = readInput("Day07_test")
    check(part1(testInputPart1) == 6440)

    val testInputPart2 = readInput("Day07_test")
    check(part2(testInputPart2) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
