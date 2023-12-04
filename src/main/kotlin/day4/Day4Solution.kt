package day4

import java.io.BufferedReader
import java.io.File

object Day4Solution {
    private var inputString: String

    init {
        val bufferedReader: BufferedReader = File("./src/main/kotlin/day4/input.txt").bufferedReader()
        inputString = bufferedReader.use { it.readText() }
    }

    fun partOne(): Int {
        var totalPoints = 0

        for ((i, line) in inputString.split("\n").filter { it.isNotBlank() }.withIndex()) {
            val winningNumbers = line
                .split(": ")[1]
                .split(" | ")[0]
                .split("\\s+".toRegex())
                .filter { it.isNotEmpty() && it.isNotBlank() }
                .map { it.toInt() }
                .toSet()
            val ourNumbers = line
                .split(": ")[1]
                .split(" | ")[1]
                .split("\\s+".toRegex())
                .filter { it.isNotEmpty() && it.isNotBlank() }
                .map { it.toInt() }
                .toSet()

            val card = Card(winningNumbers, ourNumbers)
            totalPoints += card.pointage().toInt()
        }

        return totalPoints
    }

    fun partTwo(): Int {
        val occurrences = mutableMapOf<Int, Int>()

        for ((i, line) in inputString.split("\n").filter { it.isNotBlank() }.withIndex()) {
            val id = i + 1
            occurrences[id] = occurrences.getOrDefault(id, 0) + 1

            val winningNumbers = line
                .split(": ")[1]
                .split(" | ")[0]
                .split("\\s+".toRegex())
                .filter { it.isNotEmpty() && it.isNotBlank() }
                .map { it.toInt() }
                .toSet()
            val ourNumbers = line
                .split(": ")[1]
                .split(" | ")[1]
                .split("\\s+".toRegex())
                .filter { it.isNotEmpty() && it.isNotBlank() }
                .map { it.toInt() }
                .toSet()

            val card = Card(winningNumbers, ourNumbers)
            val copiesToMake = card.numOfMatchingNumbers()

            for (cardNum in id + 1..id + copiesToMake) {
                occurrences[cardNum] = occurrences.getOrDefault(cardNum, 0) + occurrences[id]!!
            }
        }

        return occurrences.values.sum()
    }
}