package day01

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask
import common.cartesianProduct
import java.lang.Integer.parseInt

@AutoService(IDayTask::class)
class Day01 : DayTaskBase(1) {
	override fun first(): Any {
		return puzzleLines
			.map(::parseInt)
			.asSequence()
			.let { it.cartesianProduct(it) }
			.first { (a, b) -> a + b == 2020 }
			.let { (a, b) -> a * b }
	}

	override fun second(): Any? {
		return puzzleLines
			.map(::parseInt)
			.asSequence()
			.let { it.cartesianProduct(it).cartesianProduct(it) }
			.map { x ->
				val (y, a) = x
				val (b, c) = y

				return@map Triple(a, b, c)
			}
			.firstOrNull { (a, b, c) -> a + b + c == 2020 }
			?.let { (a, b, c) -> a * b * c}
	}

	override val firstResult = 355875
	override val secondResult = 140379120
}