package day7

data class Hand(val rawCards: String, val bid: Int) {
    private val cardsToNumbers = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')
    val cards = rawCards.map { cardsToNumbers.indexOf(it) }

    fun bestCombination() : List<Int> {
        val jokers = cards.groupingBy { it }.eachCount()[0] ?: 0

        for (joker in 0 until jokers) {
            for (card in cardsToNumbers.subList(1, cardsToNumbers.size)) {
            }
        }
        return listOf()
    }

    fun findHandType() : TypeOfHand {
        val countOfCards = cards.groupingBy { it }.eachCount().values

        if (5 in countOfCards) {
            return TypeOfHand.FIVE_OF_A_KIND
        }
        else if (4 in countOfCards) {
            return TypeOfHand.FOUR_OF_A_KIND
        }
        else if (2 in countOfCards && 3 in countOfCards) {
            return TypeOfHand.FULL_HOUSE
        }
        else if (3 in countOfCards) {
            return TypeOfHand.THREE_OF_A_KIND
        }
        else if (2 in countOfCards.groupingBy { it }.eachCount().values) {
            return TypeOfHand.TWO_PAIRS
        }
        else if (2 in countOfCards) {
            return TypeOfHand.ONE_PAIR
        }
        else {
            return TypeOfHand.HIGH_CARD
        }
    }
    companion object {
        enum class TypeOfHand(val precedence: Int) {
            FIVE_OF_A_KIND(6),
            FOUR_OF_A_KIND(5),
            FULL_HOUSE(4),
            THREE_OF_A_KIND(3),
            TWO_PAIRS(2),
            ONE_PAIR(1),
            HIGH_CARD(0)
        }
    }
}