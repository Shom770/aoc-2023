package day10

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

    fun startAt(x: Int, y: Int) : Int {
        val startConnector = findConnectorAt(x, y)
        cells[y][x] = startConnector

        var currentPosition = connectorToPosition(x, y).last()
        val lastNodesVisited = mutableListOf(x to y, currentPosition)
        var stepsTaken = 0

        while (x to y != currentPosition) {
            currentPosition = connectorToPosition(
                currentPosition.first,
                currentPosition.second,
                lastNodesVisited.last().first, lastNodesVisited.last().second
            ).subtract(lastNodesVisited.toSet()).takeIf { it.isNotEmpty() }?.last() ?: (x to y)
            lastNodesVisited.add(currentPosition)
            stepsTaken++
        }

        return (stepsTaken + 1) / 2
    }
}