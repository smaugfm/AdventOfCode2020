package day10

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask

@AutoService(IDayTask::class)
class Day10 : DayTaskBase(10) {
	private val voltages = puzzleLines
		.map { it.toInt() }
		.sorted()
		.let {
			listOf(0) + it + listOf(it.last() + 3)
		}

	override fun first() =
		voltages
			.zipWithNext()
			.map { (a, b) -> b - a }
			.let { voltages ->
				val ones = voltages.count { it == 1 }
				val threes = voltages.count { it == 3 }

				ones * threes
			}

	override val firstResult = 1914

	override fun second(): Any {
		val paths = mutableListOf<Long>()
		voltages
			.map { voltage ->
				voltages
					.count { x -> x in (voltage + 1)..(voltage + 3) }
			}
			.reversed()
			.mapTo(paths) { i ->
				when (i) {
					0 -> 1
					1 -> paths
						.last()
					else -> paths
						.takeLast(i)
						.sum()
				}
			}

		return paths.last()
	}

	override val secondResult = 9256148959232
}