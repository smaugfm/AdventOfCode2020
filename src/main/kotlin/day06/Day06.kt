package day06

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask

@AutoService(IDayTask::class)
class Day06 : DayTaskBase(6) {
	override fun first(): Any {
		return puzzleInput
			.split(System.lineSeparator() + System.lineSeparator())
			.map {
				it
					.replace(System.lineSeparator(), "")
					.toSet()
					.count()
			}.sum()
	}

	override val firstResultForTests = 6903
	override fun second(): Any {
		return puzzleInput
			.split(System.lineSeparator() + System.lineSeparator())
			.map { line ->
				line
					.lines()
					.map { it.toSet() }
					.reduce { a, b -> a.intersect(b) }
					.count()
			}.sum()
	}

	override val secondResultForTests = 3493
}