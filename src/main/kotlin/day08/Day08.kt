package day08

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask
import java.lang.IllegalArgumentException
import java.lang.Integer.parseInt

@AutoService(IDayTask::class)
class Day08: DayTaskBase(8) {
	private fun exec(lines: List<String>): Any {
		var cur = 0
		val visited = mutableSetOf<Int>()
		var acc = 0
		val r = Regex("\\w{3}\\s+([\\-+])(\\d+)")
		while (visited.add(cur)) {
			if (cur >= lines.size) {
				throw IllegalArgumentException(acc.toString())
			}

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

	override fun first() = exec(puzzleLines)
	override val firstResultForTests = 1179

	override fun second(): Any? {
		val indexes = mutableListOf<Int>()
		val lines = puzzleLines.toMutableList()
		lines.forEachIndexed { i, x ->
			if (x.substring(0, 3) == "nop" || x.substring(0, 3) == "jmp")
				indexes.add(i)
		}

		indexes.forEach {
			val line = lines[it]
			if (line.substring(0, 3) == "jmp") {
				lines[it] = "nop" + line.substring(3)
			} else {
				lines[it] = "jmp" + line.substring(3)
			}

			try {
				exec(lines)
				lines[it] = line
			} catch(e: IllegalArgumentException) {
				return e.message
			}
		}

		return "not right"
	}
}