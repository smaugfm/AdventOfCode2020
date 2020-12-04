package day04

import com.google.auto.service.AutoService
import common.DayTaskBase
import common.IDayTask
import kotlin.reflect.full.companionObjectInstance

@AutoService(IDayTask::class)
class Day04 : DayTaskBase(4) {
	private val allCodes =
		PassportField::class
			.sealedSubclasses
			.map { it.companionObjectInstance }
			.filterIsInstance<IPassportFieldFactory<*>>()
			.map { it.code }
			.toSet()
	private val optionalCodes = setOf(PassportField.CountryID.code)

	private fun parsePassportSet(): List<PassportRaw> =
		puzzleInput
			.split(System.lineSeparator() + System.lineSeparator())
			.map(PassportRaw::parse)

	private fun validateCodes(present: Set<String>) =
		(allCodes - present - optionalCodes).isEmpty()

	override fun first(): Any =
		parsePassportSet()
			.filter { passportRaw ->
				val presentCodes = passportRaw.fields.map { it.code }.toSet()
				validateCodes(presentCodes)
			}
			.count()

	override fun second() =
		parsePassportSet()
			.map(Passport::parse)
			.filter { passport ->
				val presentCodes = passport.fields.map { it::class.companionObjectInstance }
					.filterIsInstance<IPassportFieldFactory<*>>().map { it.code }.toSet()

				validateCodes(presentCodes)
			}
			.count()

	override val firstResultForTests = 233
	override val secondResultForTests = 111
}