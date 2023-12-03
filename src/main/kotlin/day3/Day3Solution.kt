package day3

import java.io.BufferedReader
import java.io.File

object Day3Solution {
    var inputString: String

    init {
        val bufferedReader: BufferedReader = File("./src/main/kotlin/day3/input.txt").bufferedReader()
        inputString = bufferedReader.use { it.readText() }
    }

    private fun adjacents(row: Int, column: Int, rowLength: Int, columnLength: Int) : List<List<Int>> {
        val deltas = listOf(
            listOf(0, 1),
            listOf(0, -1),
            listOf(1, 0),
            listOf(-1, 0),
            listOf(1, 1),
            listOf(1, -1),
            listOf(-1, -1),
            listOf(-1, 1)
        )
        return deltas.filter {
            (xDelta, yDelta) -> row + yDelta in 0 until rowLength && column + xDelta in 0 until columnLength
        }
    }

    fun partOne() : Int {
        val lines = inputString.split("\n")
        val numbers = mutableListOf<List<IntRange>>()
        val partNumbers = mutableSetOf<List<IntRange>>()
        val symbolRegex = "[^a-zA-Z0-9\\.]".toRegex()

        // First pass to find all the numbers
        for ((row, line) in lines.withIndex()) {
            "\\d+".toRegex().findAll(line).forEach { numbers.add(listOf(row..row, it.range))}
        }

        // Second pass to find all the adjacent numbers
        for ((row, line) in lines.withIndex()) {
            for ((col, char) in line.withIndex()) {
                val adjacentValues = adjacents(row, col, lines.size, line.length)
                    .map { (dx, dy) -> listOf(col + dx, row + dy) }

                if (symbolRegex.matches(char.toString())) {
                    for ((x, y) in adjacentValues) {
                        partNumbers += numbers.filter { y in it[0] && x in it[1] }
                    }
                }
            }
        }

        return partNumbers.sumOf { lines[it[0].first].substring(it[1]).toInt() }
    }

    fun partTwo() : Int {
        val lines = inputString.split("\n")
        val numbers = mutableListOf<List<IntRange>>()
        val symbolRegex = "[^a-zA-Z0-9\\.]".toRegex()
        var gearRatioSum = 0

        // First pass to find all the numbers
        for ((row, line) in lines.withIndex()) {
            "\\d+".toRegex().findAll(line).forEach { numbers.add(listOf(row..row, it.range))}
        }

        // Second pass to find all the adjacent numbers
        for ((row, line) in lines.withIndex()) {
            for ((col, char) in line.withIndex()) {
                val adjacentValues = adjacents(row, col, lines.size, line.length)
                    .map { (dx, dy) -> listOf(col + dx, row + dy) }

                if (symbolRegex.matches(char.toString())) {
                    val partNumbers = mutableSetOf<List<IntRange>>()

                    for ((x, y) in adjacentValues) {
                        partNumbers += numbers.filter { y in it[0] && x in it[1] }
                    }

                    gearRatioSum += if (partNumbers.size == 1) {
                        0
                    } else {
                        partNumbers
                            .map { lines[it[0].first].substring(it[1]).toInt() }
                            .reduce { acc, i -> acc * i }
                    }
                }
            }
        }

        return gearRatioSum
    }

}