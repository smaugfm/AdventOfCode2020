package day06

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask
import java.lang.Integer.parseInt

@AutoService(IDayTask::class)
class Day07 : DayTaskBase(7) {
	val containerColorAndOthersPattern =
		Regex("([\\w\\s]+)bags contain ([\\w,\\s]+).")
	val bagQuantityPattern = Regex("(\\d+) (\\w+ \\w+)")

	val noBagsColor = "no other"
	val shinyGoldColor = "shiny gold"

	sealed class BagsQuantity {
		class Some(val count: Int, val color: String) : BagsQuantity()
		object Empty : BagsQuantity()
	}

	private fun makeBagsRegistry() =
		puzzleLines.map { line ->
			val m = containerColorAndOthersPattern.matchEntire(line)!!
			val containerColor = m.groupValues[1]
			val others: List<BagsQuantity> = if (m.groupValues[2] == noBagsColor)
				listOf(BagsQuantity.Empty)
			else
				bagQuantityPattern.findAll(m.groupValues[2]).map {
					BagsQuantity.Some(parseInt(it.groupValues[1]), it.groupValues[2])
				}.toList()

			Pair(containerColor.trim(), others)
		}.toMap()

	override fun first(): Any {
		val bags = makeBagsRegistry()

		fun containsShiny(color: String): Boolean =
			when (color) {
				shinyGoldColor -> true
				else -> {
					bags[color]!!.any {
						when (it) {
							is BagsQuantity.Some -> containsShiny(it.color)
							BagsQuantity.Empty -> false
						}
					}
				}
			}

		return bags
			.keys
			.filter { it != shinyGoldColor }
			.map { containsShiny(it) }
			.filter { it }
			.count()
	}

	override val firstResultForTests = 287

	override fun second(): Any {
		val bags = makeBagsRegistry()

		fun countSubBags(color: String): Int =
			bags[color]!!.map { quantity ->
				when (quantity) {
					is BagsQuantity.Some ->
						quantity.count + quantity.count * countSubBags(quantity.color)
					is BagsQuantity.Empty -> 0
				}
			}.sum()

		return countSubBags(shinyGoldColor)
	}

	override val secondResultForTests = 48160
}
