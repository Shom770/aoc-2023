package day9

import java.io.BufferedReader
import java.io.File

object Day9Solution {
    private var inputString: String

    init {
        val bufferedReader: BufferedReader = File("./src/main/kotlin/day9/input.txt").bufferedReader()
        inputString = bufferedReader.use { it.readText() }
    }
    private fun parseInput() : List<List<Int>> {
        return inputString.split("\r\n").map {
            line -> line.split("\\s+".toRegex()).map { it.toInt() }
        }
    }

    fun partOne() : Int {
        val input = parseInput()
        var totalExtrapolation = 0

        for (line in input) {
            val differences = mutableSetOf(line)
            var lastDifference = line

            while (!lastDifference.all { it == 0 }) {
                lastDifference = lastDifference.windowed(2).map { (first, second) -> second - first }
                differences.add(lastDifference)
            }

            for (difference in differences) {
                totalExtrapolation += difference.last()
            }
        }

        return totalExtrapolation
    }

    fun partTwo() : Int {
        val input = parseInput()
        var totalExtrapolation = 0

        for (line in input) {
            val differences = mutableSetOf(line)
            var lastDifference = line

            while (!lastDifference.all { it == 0 }) {
                lastDifference = lastDifference.windowed(2).map { (first, second) -> second - first }
                differences.add(lastDifference)
            }

            totalExtrapolation += differences.reversed().map { it.first() }.reduce { acc, i ->  i - acc }
        }

        return totalExtrapolation
    }
}