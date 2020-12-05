package day05

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask

@AutoService(IDayTask::class)
class Day05 : DayTaskBase(5) {
	private fun decodeBinary(str: String, upper: Char, lower: Char): Int {
		val len = str.length
		require(str.length == len)

		var min = 0
		var maxExcl = 1 shl len
		var i = 0
		while (i < len) {
			when (str[i]) {
				upper -> maxExcl = ((maxExcl - min) / 2) + min
				lower -> min += ((maxExcl - min) / 2)
			}
			i++
		}
		return min
	}

	private fun decodeRow(str: String): Int {
		return decodeBinary(str.substring(0, 7), 'F', 'B')
	}

	private fun decodeCol(str: String): Int {
		return decodeBinary(str.substring(7, 7 + 3), 'L', 'R')
	}

	override fun first() =
		puzzleLines
			.map {
				val row = decodeRow(it)
				val col = decodeCol(it)
				row * 8 + col
			}.maxByOrNull { it }!!

	override val firstResultForTests = 991

	override fun second(): Any {
		val ids = puzzleLines
			.map {
				val row = decodeRow(it)
				val col = decodeCol(it)
				row * 8 + col
			}.toSet()
		val min = ids.minByOrNull { it }!!
		val max = ids.maxByOrNull { it }!!
		val allIds = (min..max).toSet()

		return (allIds - ids).first()
	}

	override val secondResultForTests = 534
}