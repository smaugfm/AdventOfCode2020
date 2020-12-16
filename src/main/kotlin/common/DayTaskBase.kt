package common

abstract class DayTaskBase(override val day: Int) : IDayTask {
	protected val puzzleInput: String by lazy {
		DayTaskBase::class.java.getResource(DAY_TXT_TEMPLATE.format(day))?.readText()?.trim()
			?: throw IllegalArgumentException("No file ${DAY_TXT_TEMPLATE.format(day)} found in resources.")
	}

	protected val puzzleLines by lazy {
		puzzleInput.lineSequence().filter { it.isNotBlank() }
	}

	protected val puzzleBlocks by lazy {
		puzzleInput.splitToSequence("\r\n\r\n", "\n\n", "\r\r").filter { it.isNotBlank() }
	}

	override fun runFirst(): String {
		return "Day ${"%02d".format(day)}. First task result: ${getAndCheckFirst()}"
	}

	override fun runSecond(): String {
		return "Day ${"%02d".format(day)}. Second task result: ${getAndCheckSecond()}"
	}

	private fun getAndCheckFirst() = first().also {
		if (firstResult != null)
			check(it == firstResult)
	}

	private fun getAndCheckSecond() = second().also {
		if (secondResult != null)
			check(it == secondResult)
	}

	abstract fun first(): Any
	open fun second(): Any? {
		return null
	}

	protected open val firstResult: Any? = null
	protected open val secondResult: Any? = null

	companion object {
		private const val DAY_TXT_TEMPLATE = "/day%02d.txt"
	}
}