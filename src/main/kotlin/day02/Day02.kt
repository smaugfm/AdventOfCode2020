package day02

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask
import java.lang.Integer.parseInt

@AutoService(IDayTask::class)
class Day02 : DayTaskBase(2) {
	private data class Line(val letter: Char, val min: Int, val max: Int, val sequence: String)

	private fun getLines(): List<Line> =
		puzzleLines
			.map {
				val (min, max, letter, sequence) = lineRegex.matchEntire(it)!!.groupValues.drop(1)
				Line(letter[0], parseInt(min), parseInt(max), sequence)
			}

	override fun first(): Any {
		fun checkMatch(line: Line): Boolean {
			fun toBase(char: Char) = char.toInt() - 97

			val frequencies = IntArray(26).also { frequencies ->
				line.sequence.forEach { frequencies[toBase(it)]++ }
			}

			return frequencies[toBase(line.letter)].let { frequency ->
				line.min <= frequency && line.max >= frequency
			}
		}

		return getLines()
			.filter(::checkMatch)
			.count()
	}

	override fun second(): Any {
		fun checkMatch(line: Line): Boolean {
			val first = line.sequence[line.min - 1]
			val second = line.sequence[line.max - 1]

			return (first == line.letter) xor (second == line.letter)
		}

		return getLines()
			.filter(::checkMatch)
			.count()
	}

	override val firstResultForTests = 572
	override val secondResultForTests = 306

	companion object {
		private val lineRegex = Regex("(\\d+)-(\\d+) ([a-z]): ([a-z]+)")
	}
}