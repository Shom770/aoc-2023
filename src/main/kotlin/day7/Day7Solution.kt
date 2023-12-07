package day7

import java.io.BufferedReader
import java.io.File

object Day7Solution {
    private var inputString: String

    init {
        val bufferedReader: BufferedReader = File("./src/main/kotlin/day7/input.txt").bufferedReader()
        inputString = bufferedReader.use { it.readText() }
    }

    private fun parseInput() : List<Hand> {
        return inputString.split("\\s+".toRegex()).chunked(2).filter { it.size == 2 }.map {
            (handGiven, bidGiven) -> Hand(handGiven, bidGiven.toInt())
        }
    }

    fun partOne() : Int {
        var totalBid = 0
        val hands = parseInput()
        val sortedHands = hands.sortedWith(
            compareBy(
                { it.findHandType().precedence },
                { it.cards[0] },
                { it.cards[1] },
                { it.cards[2] },
                { it.cards[3] },
                { it.cards[4] }
            )
        )

        for ((i, hand) in sortedHands.withIndex()) {
            totalBid += (i + 1) * hand.bid
        }

        return totalBid
    }

    fun partTwo() : Int {
        var totalBid = 0
        val hands = parseInput()
        println(hands[1].bestCombination())
        val sortedHands = hands.sortedWith(
            compareBy(
                { it.findHandType().precedence },
                { it.cards[0] },
                { it.cards[1] },
                { it.cards[2] },
                { it.cards[3] },
                { it.cards[4] }
            )
        )

        for ((i, hand) in sortedHands.withIndex()) {
            totalBid += (i + 1) * hand.bid
        }

        return totalBid
    }
}
