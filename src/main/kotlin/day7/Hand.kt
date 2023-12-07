package day7

data class Hand(val rawCards: String, val bid: Int, val jokerMapsTo: Int = 0) {
    private val cardsToNumbers = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')
    val cards = rawCards.map { cardsToNumbers.indexOf(it) }

    fun bestCombination() : Hand {
        val possibleCards = List(cardsToNumbers.subList(1, cardsToNumbers.size).size) { it + 1 }
        val allCombinations = mutableSetOf<Hand>()

        for (possCard in possibleCards) {
            allCombinations.add(
                Hand(
                    rawCards,
                    bid,
                    possCard
                )
            )
        }

        return allCombinations.maxBy { it.findHandType().precedence }
    }

    fun findHandType() : TypeOfHand {
        val countOfCards = cards
            .map { if (it == 0) jokerMapsTo else it }
            .groupingBy { it }
            .eachCount()
            .values

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

    override fun toString(): String {
        return cards.map { cardsToNumbers[it] }.joinToString("")
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