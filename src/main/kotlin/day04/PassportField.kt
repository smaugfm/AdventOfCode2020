package day04

import java.lang.Integer.parseInt
import java.lang.Long.parseLong
import kotlin.reflect.full.companionObjectInstance

sealed class PassportField() {
	companion object {
		fun parse(raw: PassportFieldRaw) =
			try {
				PassportField::class.sealedSubclasses
					.map { it.companionObjectInstance }
					.filterIsInstance<IPassportFieldFactory<*>>()
					.first { it.code == raw.code }
					.parse(raw.value)
			} catch (e: Throwable) {
				null
			}

		private fun parseCheckYear(value: String, min: Int, max: Int): Int {
			require(value.length == 4)
			val year = parseInt(value)
			require((min..max).contains(year))

			return year
		}
	}

	data class BirthYear(val value: Int) : PassportField() {
		companion object : IPassportFieldFactory<BirthYear> {
			override val code = "byr"
			override fun parse(value: String) =
				BirthYear(parseCheckYear(value, 1920, 2002))
		}
	}

	data class IssueYear(val value: Int) : PassportField() {
		companion object : IPassportFieldFactory<IssueYear> {
			override val code = "iyr"
			override fun parse(value: String) =
				IssueYear(parseCheckYear(value, 2010, 2020))
		}
	}

	data class ExpirationYear(val value: Int) : PassportField() {
		companion object : IPassportFieldFactory<ExpirationYear> {
			override val code = "eyr"
			override fun parse(value: String) =
				ExpirationYear(parseCheckYear(value, 2020, 2030))
		}
	}

	data class Height(val height: Int, val units: HeightUnit) : PassportField() {
		enum class HeightUnit {
			Cm,
			In
		}

		companion object : IPassportFieldFactory<PassportField> {
			override val code = "hgt"

			private val unitList =
				HeightUnit.values().joinToString("|") { it.toString().toLowerCase() }
			private val pattern = Regex("(\\d+)($unitList)")

			override fun parse(value: String): Height {
				val m = pattern.matchEntire(value) ?: throw IllegalArgumentException()
				val height = parseInt(m.groupValues[1])
				val units = HeightUnit.valueOf(m.groupValues[2].capitalize())

				when (units) {
					HeightUnit.Cm -> require((150..193).contains(height))
					HeightUnit.In -> require((59..76).contains(height))
				}

				return Height(height, units)
			}
		}
	}

	data class HairColor(val value: String) : PassportField() {
		companion object : IPassportFieldFactory<HairColor> {
			override val code = "hcl"
			private val pattern = Regex("#[a-f0-9]{6}")

			override fun parse(value: String): HairColor {
				require(pattern.matches(value))

				return HairColor(value)
			}
		}
	}

	data class EyeColor(val value: String) : PassportField() {
		companion object : IPassportFieldFactory<EyeColor> {
			private val validColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
			override val code = "ecl"
			override fun parse(value: String): EyeColor {
				require(value in validColors)

				return EyeColor(value)
			}
		}
	}

	data class PassportID(val value: Long) : PassportField() {
		companion object : IPassportFieldFactory<PassportID> {
			override val code = "pid"
			private val pattern = Regex("[0-9]{9}")

			override fun parse(value: String): PassportID {
				require(pattern.matches(value))

				return PassportID(parseLong(value))
			}
		}
	}

	data class CountryID(val country: String) : PassportField() {
		companion object : IPassportFieldFactory<CountryID> {
			override val code = "cid"
			override fun parse(value: String) = CountryID(value)
		}
	}
}

data class PassportFieldRaw(val code: String, val value: String) {
	companion object {
		fun parse(fieldStr: String): PassportFieldRaw {
			val (code, value) = fieldStr.split(":")

			return PassportFieldRaw(code, value)
		}
	}
}
