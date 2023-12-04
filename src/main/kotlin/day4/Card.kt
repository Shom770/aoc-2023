package day4

import kotlin.math.pow

class Card(private val winningNumbers: Set<Int>, private val yourNumbers: Set<Int>) {
    fun numOfMatchingNumbers() : Int {
        return winningNumbers.intersect(yourNumbers).size
    }

    fun pointage() : Double {
        return 2.0.pow(numOfMatchingNumbers() - 1)
    }
}