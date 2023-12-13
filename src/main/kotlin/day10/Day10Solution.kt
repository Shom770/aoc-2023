package day10

import java.io.BufferedReader
import java.io.File

object Day10Solution {
    private var inputString: String

    init {
        val bufferedReader: BufferedReader = File("./src/main/kotlin/day10/input.txt").bufferedReader()
        inputString = bufferedReader.use { it.readText() }
    }

    private fun parseInput() : Grid {
        return Grid(inputString.split("\r\n").map { it.toMutableList() }.toMutableList())
    }

    fun partOne() : Int {
        val grid = parseInput()
        val (rowIndex, row) = grid.cells.withIndex().first { (_, it) -> it.indexOf('S') >= 0 }
        val sheepLocation = rowIndex to row.indexOf('S')
        return grid.startAt(sheepLocation.second, sheepLocation.first)
    }
}