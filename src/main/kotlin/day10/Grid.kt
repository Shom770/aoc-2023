package day10

import kotlin.math.absoluteValue

class Grid(val cells: MutableList<MutableList<Char>>) {
    private fun neighbors(x: Int, y: Int): List<Pair<Int, Int>> {
        val deltas = listOf(
            0 to 1,
            0 to -1,
            1 to 0,
            -1 to 0,
            1 to 1,
            -1  to -1,
            1 to -1,
            -1 to 1
        )
        return deltas
            .map { (dx, dy) -> x + dx to y + dy }
            .filter { (nx, ny) -> nx in 0 until cells[0].size && ny in cells.indices }
    }

    private fun connectorToPosition(x: Int, y: Int, px: Int = 0, py: Int = 0) : Set<Pair<Int, Int>> {
        return when (cells[y][x]) {
            '|' -> setOf(x to y - 1, x to y + 1).subtract(setOf(px to py))
            '-' -> setOf(x - 1 to y, x + 1 to y).subtract(setOf(px to py))
            'L' -> setOf(x to y - 1, x + 1 to y).subtract(setOf(px to py))
            'J' -> setOf(x to y - 1, x - 1 to y).subtract(setOf(px to py))
            'F' -> setOf(x to y + 1, x + 1 to y).subtract(setOf(px to py))
            '7' -> setOf(x to y + 1, x - 1 to y).subtract(setOf(px to py))
            else -> setOf()
        }
    }

    private fun positionToConnector(x: Int, y: Int, positions: Set<Pair<Int, Int>>) : Char {
        return when (positions) {
            setOf(x to y - 1, x to y + 1) -> '|'
            setOf(x - 1 to y, x + 1 to y) -> '-'
            setOf(x to y - 1, x + 1 to y) -> 'L'
            setOf(x to y - 1, x - 1 to y) -> 'J'
            setOf(x to y + 1, x + 1 to y) -> 'F'
            setOf(x to y + 1, x - 1 to y) -> '7'
            else -> '.'
        }
    }

    private fun findConnectorAt(x: Int, y: Int) : Char {  // Used to find the type of connector for sheep
        return positionToConnector(
            x,
            y,
            neighbors(x, y).filter { (nx, ny) -> x to y in connectorToPosition(nx, ny)}.toSet()
        )
    }

    fun startAt(x: Int, y: Int): List<Pair<Int, Int>> {
        val startConnector = findConnectorAt(x, y)
        cells[y][x] = startConnector

        var currentPosition = connectorToPosition(x, y).last()
        val lastNodesVisited = mutableListOf(x to y, currentPosition)

        while (x to y != currentPosition) {
            currentPosition = connectorToPosition(
                currentPosition.first,
                currentPosition.second,
                lastNodesVisited.last().first, lastNodesVisited.last().second
            ).subtract(lastNodesVisited.toSet()).takeIf { it.isNotEmpty() }?.last() ?: (x to y)
            lastNodesVisited.add(currentPosition)
        }

        return lastNodesVisited
    }

    // Shoelace's theorem
    fun areaOfPolygon(nodes: List<Pair<Int, Int>>): Double {
        return (0.5 * (nodes.zip(nodes.subList(1, nodes.size)).fold(0) {
            acc, (pair1, pair2) ->  acc + pair1.first * pair2.second
        } + nodes.last().first * nodes.first().second - nodes.zip(nodes.subList(1, nodes.size)).fold(0) {
            acc, (pair1, pair2) ->  acc + pair2.first * pair1.second
        } - nodes.first().first * nodes.last().second)).absoluteValue
    }

    // Pick's theorem
    fun pointsInPolygon(areaOfPolygon: Double, boundaryPoints: Int): Double {
        return areaOfPolygon - boundaryPoints / 2 + 1
    }
}