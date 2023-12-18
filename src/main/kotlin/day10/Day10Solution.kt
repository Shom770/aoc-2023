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
        return Grid(inputString.split("\n").map { it.toMutableList() }.toMutableList())
    }

    fun partOne() : Int {
        val grid = parseInput()
        val (rowIndex, row) = grid.cells.withIndex().first { (_, it) -> it.indexOf('S') >= 0 }
        val sheepLocation = rowIndex to row.indexOf('S')
        return (grid.startAt(sheepLocation.second, sheepLocation.first).size + 1) / 2
    }

    fun partTwo(): Int {
        val grid = parseInput()
        val (rowIndex, row) = grid.cells.withIndex().first { (_, it) -> it.contains('S') }
        val sheepLocation = rowIndex to row.indexOf('S')

        val nodes = grid.startAt(sheepLocation.second, sheepLocation.first)
        return grid.pointsInPolygon(grid.areaOfPolygon(nodes), nodes.size).toInt()
    }
}