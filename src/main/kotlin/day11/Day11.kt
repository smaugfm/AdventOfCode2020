package day11

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask
import java.lang.IllegalArgumentException

@AutoService(IDayTask::class)
class Day11 : DayTaskBase(11) {
	enum class Cell {
		Empty,
		Occupied,
		Floor
	}

	@Suppress("EqualsOrHashCode")
	class Board(private val grid: List<List<Cell>>) {
		private val width = grid[0].size
		private val height = grid.size

		init {
			require(grid.all { it.size == grid[0].size })
		}

		fun advance() =
			(0 until height).map { y ->
				(0 until width).map { x ->
					val adjacent = adjacentSeats(x, y)
					val res = when (value(x, y)) {
						Cell.Empty -> if (adjacent.none { it == Cell.Occupied }) Cell.Occupied else Cell.Empty
						Cell.Occupied -> if (adjacent.count { it == Cell.Occupied } >= 4) Cell.Empty else Cell.Occupied
						Cell.Floor -> Cell.Floor
					}
					res
				}
			}.let(::Board)

		private fun value(x: Int, y: Int) = grid[y][x]

		private fun adjacentSeats(x: Int, y: Int): List<Cell> {
			val adjacent = mutableListOf<Cell>()

			if (x > 0) adjacent.add(value(x - 1, y))
			if (x < width - 1) adjacent.add(value(x + 1, y))
			if (y > 0) adjacent.add(value(x, y - 1))
			if (y < height - 1) adjacent.add(value(x, y + 1))
			if (x > 0 && y > 0) adjacent.add(value(x - 1, y - 1))
			if (x < width - 1 && y < height - 1) adjacent.add(value(x + 1, y + 1))
			if (x > 0 && y < height - 1) adjacent.add(value(x - 1, y + 1))
			if (x < width - 1 && y > 0) adjacent.add(value(x + 1, y - 1))

			check(adjacent.size in 1..8)

			return adjacent
		}

		fun count(type: Cell) =
			grid.sumBy { row -> row.count { it == type } }

		override fun equals(other: Any?): Boolean =
			(other as? Board)?.grid == grid

		companion object {
			fun parse(input: String) =
				input
					.lines()
					.map { rowStr ->
						rowStr.map {
							when (it) {
								'L' -> Cell.Empty
								'#' -> Cell.Occupied
								'.' -> Cell.Floor
								else -> throw IllegalArgumentException()
							}
						}
					}.let(::Board)
		}
	}

	override fun first(): Any {
		val board = Board.parse(puzzleInput)

		var prev = board
		while (true) {
			val next = prev.advance()
			if (next == prev) {
				break
			}
			prev = next
		}

		return prev.count(Cell.Occupied)
	}

	override val firstResult = 2418
}