package day3

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask
import java.lang.IllegalArgumentException

@AutoService(IDayTask::class)
class Day03 : DayTaskBase(3) {
	private class Field(private val initialField: Array<BooleanArray>) {
		val width = initialField[0].size.also { size ->
			initialField.all { it.size == size }
		}
		val height = initialField.size

		fun getAt(coordinates: XYCoordinates): Boolean {
			val (x, y) = coordinates
			require(y < height) { "Y Out of range. Y: $y, Height: $height" }

			val col = if (x < width) x else x % width

			return initialField[y][col]
		}

		companion object {
			fun parse(lines: List<String>): Field =
				lines
					.map { line ->
						line
							.map {
								when (it) {
									'#' -> true
									'.' -> false
									else -> throw IllegalArgumentException("Illegal char in map: $it")
								}
							}
							.toBooleanArray()
					}
					.toTypedArray()
					.let(::Field)
		}
	}

	private data class XYCoordinates(val x: Int, val y: Int)

	private fun getTreesOnSlope(right: Int, down: Int): Int {
		val field = Field.parse(puzzleLines)

		val path = generateSequence(XYCoordinates(0, 0)) { (x, y) ->
			val nextX = x + right
			val nextY = y + down
			if (nextY >= field.height)
				null
			else
				XYCoordinates(nextX, nextY)
		}

		return path.map(field::getAt).count { it }
	}

	override fun first() = getTreesOnSlope(3, 1)
	override val firstResultForTests = 167

	override fun second(): Any {
		val trees = listOf(
			getTreesOnSlope(1, 1),
			getTreesOnSlope(3, 1),
			getTreesOnSlope(5, 1),
			getTreesOnSlope(7, 1),
			getTreesOnSlope(1, 2),
		                  )

		return trees.reduce { a, b -> a * b }
	}

	override val secondResultForTests = 736527114
}