package day08

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask
import java.lang.Integer.parseInt

@AutoService(IDayTask::class)
class Day08 : DayTaskBase(8) {
	class InfiniteLoopException(val acc: Int) : Exception(acc.toString())

	private fun exec(lines: List<String>): Int {
		var cur = 0
		val visited = mutableSetOf<Int>()
		var acc = 0
		val r = Regex("\\w{3}\\s+([\\-+])(\\d+)")
		while (cur < lines.size) {
			if (!visited.add(cur))
				throw InfiniteLoopException(acc)

			val line = lines[cur]
			val param = parseInt(r.matchEntire(line)!!.groupValues[2])
			val sign = r.matchEntire(line)!!.groupValues[1]
			when (line.substring(0, 3)) {
				"nop" -> cur++
				"acc" -> {
					acc = if (sign == "+") acc + param else acc - param
					cur++
				}
				"jmp" -> {
					cur = if (sign == "+") cur + param else cur - param
				}
			}
		}

		return acc
	}

	override fun first() = try {
		exec(puzzleLines.toList())
		"No loop"
	} catch (e: InfiniteLoopException) {
		e.acc
	}

	override val firstResult = 1179

	override fun second(): Any {
		val lines = puzzleLines.toList()
		val linesCopy = lines.toMutableList()

		for ((i, line) in lines.withIndex()) {
			linesCopy[i] = when (line.substring(0, 3)) {
				"nop" -> {
					"jmp" + lines[i].substring(3)
				}
				"jmp" -> {
					"nop" + lines[i].substring(3)
				}
				else -> continue;
			}

			try {
				return exec(linesCopy)
			} catch (e: InfiniteLoopException) {
				linesCopy[i] = line
			}
		}

		return "All loops"
	}

	override val secondResult = 1089
}