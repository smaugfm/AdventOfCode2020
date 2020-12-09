package day09

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask
import common.product
import java.lang.Integer.parseInt
import java.util.*
import javax.xml.bind.DatatypeConverter.parseLong

class FixedQueue<T>(private val queue: Queue<T>) : Iterable<T> {
	fun push(next: T) {
		queue.offer(next)
		queue.remove()
	}

	override fun iterator() = queue.iterator()
}

@AutoService(IDayTask::class)
class Day09 : DayTaskBase(9) {
	override fun first(): Any {
		val width = 25

		val prev =
			ArrayDeque<Long>(width)
				.also {
					it.addAll(puzzleLines.take(width).map(::parseLong))
				}.let(::FixedQueue)

		fun check(target: Long, list: Iterable<Long>): Boolean =
			list.product(list).none { (x, y) -> x + y == target }

		puzzleLines
			.drop(width)
			.forEach {
				val cur = parseLong(it)

				if (check(cur, prev))
					return cur
				else {
					prev.push(cur)
				}
			}

		return "nothing"
	}

	override val firstResult = 1212510616L
	override fun second(): Any {

		fun check(target: Long, list: Iterable<Long>): Int {
			var acc = 0L
			list.withIndex().forEach { (i, x) ->
				acc += x
				if (acc == target)
					return i
			}

			return -1
		}

		val all = puzzleLines.map(::parseLong)
		all.indices.forEach { index ->
			val sublist = all.drop(index)
			val len = check(firstResult, sublist)
			if (len > 0)
				return sublist.subList(0, len).let {
					it.minOrNull()!! + it.maxOrNull()!!
				}
		}

		return "nothing"
	}

	override val secondResult = 171265123L
}