import common.IDayTask
import java.util.*
import kotlin.streams.toList

val days = ServiceLoader.load(IDayTask::class.java).toList().map { it.day to it }.toMap()

fun currentDayTask(): IDayTask = days[9] ?: error("No DayTask found!")

fun main(args: Array<String>) {
	val task = currentDayTask()
	args[0].let {
		when (it) {
			"first" -> println(task.runFirst())
			"second" -> println(task.runSecond())
			"all" -> all()
			else -> println("No argument provided.")
		}
	}
}

fun all() {
	days
		.toList()
		.map {
			it.second.let {
				Pair({ it.runFirst() }, { it.runSecond() })
			}
		}.flatMap { it.toList() }
		.parallelStream()
		.map { it() }
		.toList()
		.sorted()
		.forEach { println(it) }
}
