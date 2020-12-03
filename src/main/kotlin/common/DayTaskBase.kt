package common

import java.lang.IllegalArgumentException

abstract class DayTaskBase(override val day: Int) : IDayTask {
	private val puzzleInput: String by lazy {
		DayTaskBase::class.java.getResource(DAY_TXT_TEMPLATE.format(day))?.readText()?.trim()
			?: throw IllegalArgumentException("No file ${DAY_TXT_TEMPLATE.format(day)} found in resources.")
	}

	protected val puzzleLines by lazy {
		puzzleInput.split(System.lineSeparator()).filter { it.isNotBlank() }
	}

	override fun runFirst() {
		println("Day $day. First task result: ${firstResult()}")
	}

	override fun runSecond() {
		println("Day $day. Second task result: ${secondResult()}")
	}

	private fun firstResult() = first().also {
		if (firstResultForTests != null)
			check(it == firstResultForTests)
	}

	private fun secondResult() = second().also {
		if (secondResultForTests != null)
			check(it == secondResultForTests)
	}

	abstract fun first(): Any
	open fun second(): Any? {
		return null
	}

	protected open val firstResultForTests: Any? = null
	protected open val secondResultForTests: Any? = null

	companion object {
		private const val DAY_TXT_TEMPLATE = "/day%02d.txt"
	}
}