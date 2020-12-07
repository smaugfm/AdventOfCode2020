package day06

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask
import java.lang.Integer.parseInt

@AutoService(IDayTask::class)
class Day07 : DayTaskBase(7) {
	val linePattern =
		Regex("([\\w\\s]+)bags contain ([\\w,\\s]+).")
	val bagPattern = Regex("(\\d+) (\\w+ \\w+)")

	sealed class QuantityOfBags {
		class Bags(val count: Int, val color: String) : QuantityOfBags()
		class NoBags() : QuantityOfBags()
	}

	override fun first(): Any {
		val bags = puzzleLines.map { line ->
			val m = linePattern.matchEntire(line)!!
			val containerColor = m.groupValues[1]
			val others = if (m.groupValues[2] == "no other")
				listOf("no other")
			else
				bagPattern.findAll(m.groupValues[2]).map {
					it.groupValues[2]
				}.toList()

			Pair(containerColor.trim(), others)
		}.toMap()

		fun containShiny(color: String): Boolean {
			if (color.trim() == "no other")
				return false;
			if (color == "shiny gold")
				return true;
			return bags[color]!!.any {
				containShiny(it)
			}
		}

		return bags.keys.filter { it != "shiny gold" }
			.map { containShiny(it) }.filter { it }.count()
	}

	override val firstResultForTests = 287


	override fun second(): Any {
		val bags = puzzleLines.map { line ->
			val m = linePattern.matchEntire(line)!!
			val containerColor = m.groupValues[1]
			val others: List<QuantityOfBags> = if (m.groupValues[2] == "no other")
				listOf(QuantityOfBags.NoBags())
			else
				bagPattern.findAll(m.groupValues[2]).map {
					QuantityOfBags.Bags(parseInt(it.groupValues[1]), it.groupValues[2])
				}.toList()

			Pair(containerColor.trim(), others)
		}.toMap()

		fun countBags(color: String): Int {
			val innerBags = bags[color]!!
			val res = innerBags.map { quantity ->
				when (quantity) {
					is QuantityOfBags.Bags -> {
						quantity.count + quantity.count * countBags(quantity.color)
					}
					is QuantityOfBags.NoBags -> 0
				}
			}.sum()

			return res
		}

		return countBags("shiny gold")
	}

	override val secondResultForTests = 48160
}
