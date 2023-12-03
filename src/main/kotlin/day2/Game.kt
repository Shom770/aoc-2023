package day2

data class Game(val id: Int, val turns: List<List<Int>>) {
    private fun maxOfEach(): List<Int> {
        return turns.map {
            it.max()
        }
    }

    fun exceeds(redCubes: Int, greenCubes: Int, blueCubes: Int) : Boolean {
        val requirements = listOf(redCubes, greenCubes, blueCubes)
        return maxOfEach().withIndex().any { (i, it) -> it > requirements[i]}
    }

    fun powersOfMax(): Int {
        return maxOfEach().reduce { acc, i ->  acc * i }
    }
}
