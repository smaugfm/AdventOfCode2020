package day04

class Passport(val fields: Set<PassportField>) {
	companion object {
		fun parse(raw: PassportRaw) =
			Passport(
				raw
					.fields
					.mapNotNull(PassportField::parse)
					.toSet()
			        )
	}
}

class PassportRaw(val fields: Set<PassportFieldRaw>) {
	companion object {
		fun parse(passportStr: String) =
			passportStr
				.replace(System.lineSeparator(), " ")
				.split(Regex("\\s+"))
				.map { PassportFieldRaw.parse(it) }
				.toSet()
				.let(::PassportRaw)
	}
}