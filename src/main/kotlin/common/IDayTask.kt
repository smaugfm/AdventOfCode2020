package common

interface IDayTask {
	val day: Int
	fun runFirst(): String
	fun runSecond(): String
}