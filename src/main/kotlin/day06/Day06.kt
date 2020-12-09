package day06

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask

@AutoService(IDayTask::class)
class Day06 : DayTaskBase(6) {
	private fun process(reducer: (Set<Char>, Set<Char>) -> Set<Char>) =
		puzzleBlocks
			.map { line ->
				line
					.lines()
					.map { it.toSet() }
					.reduce(reducer)
					.count()
			}.sum()

	override fun first() = process { a, b -> a.union(b) }
	override val firstResult = 6903

	override fun second() = process { a, b -> a.intersect(b) }
	override val secondResult = 3493
}