package common

abstract class DayTaskBase(override val day: Int) : IDayTask {
	protected val puzzleInput: String by lazy {
		DayTaskBase::class.java.getResource(DAY_TXT_TEMPLATE.format(day))?.readText()?.trim()
			?: throw IllegalArgumentException("No file ${DAY_TXT_TEMPLATE.format(day)} found in resources.")
	}

	protected val puzzleLines by lazy {
		puzzleInput.split(newLine).filter { it.isNotBlank() }
	}

	protected val puzzleBlocks by lazy {
		puzzleInput.split(blankLine).filter { it.isNotBlank() }
	}

	override fun runFirst(): String {
		return "Day ${"%02d".format(day)}. First task result: ${firstResult()}"
	}

	override fun runSecond(): String {
		return "Day ${"%02d".format(day)}. Second task result: ${secondResult()}"
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