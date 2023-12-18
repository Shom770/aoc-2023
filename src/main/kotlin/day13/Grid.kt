package day13

import kotlin.math.absoluteValue

data class Grid(val cells: List<List<Char>>) {
    private fun findAllSprings(): List<Pair<Int, Int>> {
        return cells.indices.flatMap {
            rowIdx -> cells[rowIdx].indices.filter { cells[rowIdx][it] == '#' }.map { rowIdx to it }
        }
    }

    private fun <T> List<List<T>>.transpose(): List<List<T>> {
        return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
    }

    private fun <T> List<T>.nDifferentElements(other: List<T>) : Int {
        return this.zip(other).filter { (first, second) -> first != second }.size
    }

    fun findVerticalReflections(): Pair<Int, Int>? {
        val springs = findAllSprings()
        val springsByColumn = springs.groupBy { it.second }.values.sortedBy { it[0].second }
        val totalColumns = springsByColumn.size
        val allReflections = mutableSetOf<Pair<Int, Int>>()

        for ((column, nextColumn) in cells[0].indices.zip(cells[0].indices.toList().subList(1, totalColumns))) {
            // Possible reflection
            if (springsByColumn[column].map { it.first } == springsByColumn[nextColumn].map { it.first }) {
                // 0 1 2 3 4, 5 6 7 8 9
                val reflectionLeft = springsByColumn
                    .subList(0, nextColumn)
                    .map { col -> col.map { (column + 0.5 - it.second).absoluteValue }}
                val reflectionRight = springsByColumn
                    .subList(nextColumn, totalColumns)
                    .map { col -> col.map { (column + 0.5 - it.second).absoluteValue }}
                val possibleReflection = reflectionLeft.reversed().zip(reflectionRight)

                if (possibleReflection.all { (first, second) -> first == second}) {
                    allReflections.add(nextColumn to possibleReflection.size)
                }
            }
        }

        return allReflections.maxByOrNull { it.second }
    }

    fun findVerticalReflectionsWithSmudge(): Pair<Int, Int>? {
        val cellsTransposed = cells.transpose()
        val totalColumns = cellsTransposed.size
        val allReflections = mutableSetOf<Pair<Int, Int>>()

        for ((_, nextColumn) in cellsTransposed.indices.zip(cellsTransposed.indices.toList().subList(1, totalColumns))) {
            val reflectionLeft = cellsTransposed.subList(0, nextColumn)
            val reflectionRight = cellsTransposed.subList(nextColumn, totalColumns)
            val possibleReflection = reflectionLeft.reversed().zip(reflectionRight)
            val smudges = possibleReflection.map { (first, second) -> first.nDifferentElements(second) }

            if (smudges.count { it == 1 } == 1 && smudges.count { it == 0 } == smudges.size - 1) {
                allReflections.add(nextColumn to possibleReflection.size)
            }
        }

        return allReflections.maxByOrNull { it.second }
    }

    fun findHorizontalReflections(): Pair<Int, Int>? {
        val springs = findAllSprings()
        val springsByRow = springs.groupBy { it.first }.values.sortedBy { it[0].first }
        val totalRows = springsByRow.size
        val allReflections = mutableSetOf<Pair<Int, Int>>()

        for ((row, nextRow) in cells.indices.zip(cells.indices.toList().subList(1, totalRows))) {
            // Possible reflection
            if (springsByRow[row].map { it.second } == springsByRow[nextRow].map { it.second }) {
                // 0 1 2 3 4, 5 6 7 8 9
                val reflectionUp = springsByRow
                    .subList(0, nextRow)
                    .map { springRow -> springRow.map { (row + 0.5 - it.first).absoluteValue }}
                val reflectionDown = springsByRow
                    .subList(nextRow, totalRows)
                    .map { springRow -> springRow.map { (row + 0.5 - it.first).absoluteValue }}
                val possibleReflection = reflectionUp.reversed().zip(reflectionDown)

                if (possibleReflection.all { (first, second) -> first == second}) {
                    allReflections.add(nextRow to possibleReflection.size)
                }
            }
        }

        return allReflections.maxByOrNull { it.second }
    }

    fun findHorizontalReflectionsWithSmudge(): Pair<Int, Int>? {
        val totalRows = cells.size
        val allReflections = mutableSetOf<Pair<Int, Int>>()

        for ((_, nextRow) in cells.indices.zip(cells.indices.toList().subList(1, totalRows))) {
            val reflectionUp = cells.subList(0, nextRow)
            val reflectionDown = cells.subList(nextRow, totalRows)
            val possibleReflection = reflectionUp.reversed().zip(reflectionDown)

            val smudges = possibleReflection.map { (first, second) -> first.nDifferentElements(second) }
            if (smudges.count { it == 1 } == 1 && smudges.count { it == 0 } == smudges.size - 1) {
                allReflections.add(nextRow to possibleReflection.size)
            }
        }

        return allReflections.maxByOrNull { it.second }
    }
}