package day2

import java.io.BufferedReader
import java.io.File

object Day2Solution {
    var inputString: String

    init {
        val bufferedReader: BufferedReader = File("./src/main/kotlin/day2/input.txt").bufferedReader()
        inputString = bufferedReader.use { it.readText() }
    }

    fun partOne() : Int {
        val redCubes = 12
        val greenCubes = 13
        val blueCubes = 14
        var totalIDs = 0

        for (line in inputString.split("\n")) {
            val id = "(?<=Game )(\\d+)".toRegex().find(line)!!.value.toInt()
            val turns = mutableListOf<MutableList<Int>>(
                mutableListOf(),
                mutableListOf(),
                mutableListOf()
            )

            for (turn in line.split(":")[1].split(";")) {
                val redCubes = "(\\d+)(?= red)".toRegex().find(turn)?.value?.toInt() ?: 0
                val greenCubes = "(\\d+)(?= green)".toRegex().find(turn)?.value?.toInt() ?: 0
                val blueCubes = "(\\d+)(?= blue)".toRegex().find(turn)?.value?.toInt() ?: 0

                turns[0].add(redCubes)
                turns[1].add(greenCubes)
                turns[2].add(blueCubes)
            }

            val game = Game(id, turns)

            if (!game.exceeds(redCubes, greenCubes, blueCubes)) {
                totalIDs += game.id
            }
        }

        return totalIDs
    }

    fun partTwo() : Int {
        var totalPower = 0

        for (line in inputString.split("\n")) {
            val id = "(?<=Game )(\\d+)".toRegex().find(line)!!.value.toInt()
            val turns = mutableListOf<MutableList<Int>>(
                mutableListOf(),
                mutableListOf(),
                mutableListOf()
            )

            for (turn in line.split(":")[1].split(";")) {
                val redCubes = "(\\d+)(?= red)".toRegex().find(turn)?.value?.toInt() ?: 0
                val greenCubes = "(\\d+)(?= green)".toRegex().find(turn)?.value?.toInt() ?: 0
                val blueCubes = "(\\d+)(?= blue)".toRegex().find(turn)?.value?.toInt() ?: 0

                turns[0].add(redCubes)
                turns[1].add(greenCubes)
                turns[2].add(blueCubes)
            }

            val game = Game(id, turns)
            totalPower += game.powersOfMax()
        }

        return totalPower
    }
}