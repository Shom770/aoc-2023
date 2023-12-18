package day14

import java.io.BufferedReader
import java.io.File

object Day14Solution {
    private var inputString: String

    init {
        val bufferedReader: BufferedReader = File("./src/main/kotlin/day14/input.txt").bufferedReader()
        inputString = bufferedReader.use { it.readText() }
    }

    private fun parseInput():Grid {
        return Grid(inputString.split("\n").map { it.toList() })
    }

    fun partOne(): Int {
        val grid = parseInput()
        return grid.rollRocksNorth().sum()
    }

    fun partTwo(): Int {
        return 0
    }
}
