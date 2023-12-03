package day1

import java.io.BufferedReader
import java.io.File
import java.lang.NullPointerException

object Day1Solution {
    var inputString: String

    init {
        val bufferedReader: BufferedReader = File("./src/main/kotlin/day1/input.txt").bufferedReader()
        inputString = bufferedReader.use { it.readText() }
    }

    fun partOne() : Int {
        var sumOfDigits = 0

        for (line in inputString.split("\n")) {
            sumOfDigits += (line.first { it.isDigit() }.toString() + line.last { it.isDigit() }.toString()).toInt()
        }

        return sumOfDigits
    }

    fun partTwo() : Int {
        var sumOfDigits = 0
        val wordToString = hashMapOf<String, String>(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )

        for (line in inputString.split("\n")) {
            val firstRealDigit = line.indexOfFirst { it.isDigit() }.let {
                if (it == -1) 100 else it
            }
            val lastRealDigit = line.indexOfLast { it.isDigit() }.let {
                if (it == -1) -100 else it
            }

            val firstWordFound = "(one|two|three|four|five|six|seven|eight|nine)".toRegex().find(line)
            val firstWordDigit = (firstWordFound?.range?.first ?: 100)

            val lastWordFound = "(eno|owt|eerht|ruof|evif|xis|neves|thgie|enin)".toRegex().find(line.reversed())
            val lastWordDigit = line.length - 1 - (lastWordFound?.range?.last ?: 100)

            val finalDigits = if (firstRealDigit < firstWordDigit) {
                line[firstRealDigit]
            } else {
                wordToString[firstWordFound!!.value]
            }.toString() + if (lastRealDigit > lastWordDigit) {
                line[lastRealDigit]
            } else {
                wordToString[lastWordFound!!.value.reversed()]
            }.toString()

            sumOfDigits += finalDigits.toInt()
        }

        return sumOfDigits
    }
}