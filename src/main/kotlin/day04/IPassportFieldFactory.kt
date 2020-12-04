package day04

interface IPassportFieldFactory<out T : PassportField> {
	val code: String
	fun parse(value: String): T
}