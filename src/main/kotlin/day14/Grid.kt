package day14

import kotlin.math.absoluteValue

data class Grid(val cells: List<List<Char>>) {
    private fun <T> List<List<T>>.transpose(): List<List<T>> {
        return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
    }

    fun rollRocksNorth(): List<Int> {
        val transposedCells = cells.transpose()
        val newLocations = mutableListOf<List<Int>>()

        for (column in transposedCells) {
            val emptyBlocks = column.indices.filter { column[it] == '.'}.toMutableList()
            val cubeBlocks = column.indices.filter { column[it] == '#' }
            val roundedBlocks = column.indices.filter { column[it] == 'O' }.toMutableList()
            var previousRoundedBlocks: MutableList<Int> = roundedBlocks.toMutableList()

            for ((i, block) in roundedBlocks.withIndex()) {
                // If a cube is right ahead of it or another rounded block, then just ignore it entirely.
                if (block - 1 in cubeBlocks || block - 1 in roundedBlocks) {
                    continue
                }

                // Recalculate empty blocks at the start of every loop
                emptyBlocks += previousRoundedBlocks.subtract(roundedBlocks)
                previousRoundedBlocks = roundedBlocks.toMutableList()

                if (!emptyBlocks.all { it > block }) {
                    val blocksInWay = cubeBlocks.filter { it < block }
                    val blockToUse = if (blocksInWay.isNotEmpty()) {
                        emptyBlocks.filter { it > blocksInWay.max() }.minOrNull()
                    }
                    else {
                        emptyBlocks.minOrNull()
                    }.also { emptyBlocks.remove(it) }
                    roundedBlocks[i] = blockToUse ?: block
                }
            }

            newLocations.add(roundedBlocks)
        }

        return newLocations.flatMap { column -> column.map { cells.size - it } }
    }

    fun rollRocksInCycle(): List<Int> {
        val transposedCells = cells.transpose()
        val newLocations = mutableListOf<List<Int>>()

        for (column in transposedCells) {
            val emptyBlocks = column.indices.filter { column[it] == '.'}.toMutableList()
            val cubeBlocks = column.indices.filter { column[it] == '#' }
            val roundedBlocks = column.indices.filter { column[it] == 'O' }.toMutableList()
            var previousRoundedBlocks: MutableList<Int> = roundedBlocks.toMutableList()

            for ((i, block) in roundedBlocks.withIndex()) {
                // If a cube is right ahead of it or another rounded block, then just ignore it entirely.
                if (block - 1 in cubeBlocks || block - 1 in roundedBlocks) {
                    continue
                }

                // Recalculate empty blocks at the start of every loop
                emptyBlocks += previousRoundedBlocks.subtract(roundedBlocks)
                previousRoundedBlocks = roundedBlocks.toMutableList()

                if (!emptyBlocks.all { it > block }) {
                    val blocksInWay = cubeBlocks.filter { it < block }
                    val blockToUse = if (blocksInWay.isNotEmpty()) {
                        emptyBlocks.filter { it > blocksInWay.max() }.minOrNull()
                    }
                    else {
                        emptyBlocks.minOrNull()
                    }.also { emptyBlocks.remove(it) }
                    roundedBlocks[i] = blockToUse ?: block
                }
            }

            newLocations.add(roundedBlocks)
        }

        return newLocations.flatMap { column -> column.map { cells.size - it } }
    }
}