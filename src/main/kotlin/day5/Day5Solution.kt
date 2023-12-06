package day5

import java.io.BufferedReader
import java.io.File

object Day5Solution {
    private var inputString: String

    init {
        val bufferedReader: BufferedReader = File("./src/main/kotlin/day5/input.txt").bufferedReader()
        inputString = bufferedReader.use { it.readText() }
    }

    private fun extractLongs(input: String) : List<Long> {
        return "\\d+".toRegex().findAll(input).map { it.value.toLong() }.toList()
    }

    private fun parseInput() : List<List<Long>> {
        return inputString.split("\n\n").map { extractLongs(it)  }
    }

    private fun mapSeedToNewValue(seed: Long, mappingOfRanges: Map<LongRange, Long>) : Long {
        val destinationRangeToUse = mappingOfRanges.keys.maxByOrNull { seed  } ?: 0..0
        val differenceInRange = destinationRangeToUse.start.toLong() - mappingOfRanges.getOrDefault(destinationRangeToUse, 0)
        return seed - differenceInRange
    }

    private fun splitAndTransformRanges(seed: LongRange, sourceRange: LongRange, destinationStart: Long) : Set<LongRange> {
        val transformation = sourceRange.first - destinationStart
        val newSeeds = mutableSetOf<LongRange>()

        // Source range fully in seed
        if (sourceRange.first in seed && sourceRange.last in seed) {
            newSeeds.add((sourceRange.first - transformation)..(sourceRange.last - transformation))
            newSeeds.add(seed.first until sourceRange.first)
            newSeeds.add(sourceRange.last + 1..seed.last)
        }
        // Seed fully inside source range
        else if (seed.first in sourceRange && seed.last in sourceRange) {
            newSeeds.add((seed.first - transformation)..(seed.last - transformation))
        }
        // Partially in seed (sourceRange is towards the end of the seed)
        else if (sourceRange.first in seed) {
            newSeeds.add((sourceRange.first - transformation)..(seed.last - transformation))
            // Add the split ranges (in this case, whatever remaining of sourceRange)
            if (sourceRange.first > seed.first) {
                newSeeds.add(seed.first..sourceRange.first)
            }
        }
        // Partially in seed (sourceRange is intersects in the beginning of the seed)
        else if (sourceRange.last in seed) {
            newSeeds.add((seed.first - transformation)..(sourceRange.last - transformation))
            if (seed.last > sourceRange.last) {
                newSeeds.add(sourceRange.last..seed.last)
            }
        }
        // No intersection at all
        else {
            newSeeds.add(seed)
        }

        println(seed)
        println(sourceRange)
        println(destinationStart)
        println(newSeeds)
        println("----")
        return newSeeds
    }

    fun partOne() : Long {
        val lines = parseInput()
        var seeds = lines[0]
        val mappings = lines.subList(1, lines.size).map { it.chunked(3) }

        for (map in mappings) {
            val mapToNewValue = mutableMapOf<LongRange, Long>()
            for ((destinationStart, sourceStart, rangeLength) in map) {
                mapToNewValue[sourceStart until sourceStart + rangeLength] = destinationStart
            }

            seeds = seeds.map {
                seed -> mapSeedToNewValue(seed, mapToNewValue)
            }
        }

        return seeds.min()
    }

    fun partTwo() : Long {
        val lines = parseInput()
        var seeds = lines[0]
            .chunked(2)
            .map { (rangeStart, rangeLength) -> LongRange(rangeStart, rangeStart + rangeLength - 1) }
            .toMutableSet()
        val mappings = lines.subList(1, lines.size).map { it.chunked(3) }

        for ((i, map) in mappings.withIndex()) {
            val allSeedsPast = seeds.toSet()

            for ((destinationStart, sourceStart, rangeLength) in map) {
                for (seed in seeds.toSet()) {
                    seeds += splitAndTransformRanges(
                        seed,
                        sourceStart until sourceStart + rangeLength,
                        destinationStart
                    )
                }
            }

            if (i == mappings.size - 1) {
                seeds = seeds.subtract(allSeedsPast).toMutableSet()
            }
        }

        return seeds.minOf { it.first }
    }
}