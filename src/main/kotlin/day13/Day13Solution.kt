package day13

import java.io.BufferedReader
import java.io.File

object Day13Solution {
    private var inputString: String

    init {
        val bufferedReader: BufferedReader = File("./src/main/kotlin/day13/input.txt").bufferedReader()
        inputString = bufferedReader.use { it.readText() }
    }

    private fun parseInput(): List<Grid> {
        return inputString.split("\n\n").map {
            pattern -> Grid(pattern.split("\n").map { it.toList() })
        }
    }

    fun partOne(): Int {
        val patterns = parseInput()
        var summarized = 0

        for (pattern in patterns) {
            val verticalReflection = pattern.findVerticalReflections()
            val horizontalReflection = pattern.findHorizontalReflections()

            summarized += if (verticalReflection != null && horizontalReflection != null) {
                // Vertical reflection has more points
                if (listOf(verticalReflection, horizontalReflection).maxBy { it.second } == verticalReflection) {
                    verticalReflection.first
                } else {
                    horizontalReflection.first * 100
                }
            } else verticalReflection?.first ?: (horizontalReflection!!.first * 100)
        }

        return summarized
    }

    fun partTwo(): Int {
        val patterns = parseInput()
        var summarized = 0

        for (pattern in patterns) {
            val verticalReflection = pattern.findVerticalReflectionsWithSmudge()
            val horizontalReflection = pattern.findHorizontalReflectionsWithSmudge()

            summarized += if (verticalReflection != null && horizontalReflection != null) {
                // Vertical reflection has more points
                if (listOf(verticalReflection, horizontalReflection).maxBy { it.second } == verticalReflection) {
                    verticalReflection.first
                } else {
                    horizontalReflection.first * 100
                }
            } else verticalReflection?.first ?: (horizontalReflection!!.first * 100)
        }

        return summarized
    }
}
