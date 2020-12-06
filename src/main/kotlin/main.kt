import common.IDayTask
import java.util.*

val days = ServiceLoader.load(IDayTask::class.java).toList().map { it.day to it }.toMap()

fun currentDayTask(): IDayTask = days[6]!!

fun main(args: Array<String>) {
	val task = currentDayTask()
	when {
		args[0] == "first" -> task.runFirst()
		args[0] == "second" -> task.runSecond()
		else -> println("No argument provided.")
	}
}
