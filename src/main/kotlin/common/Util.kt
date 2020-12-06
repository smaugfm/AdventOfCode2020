package common

fun <T, U> Iterable<T>.product(other: Iterable<U>) =
	this.flatMap { fromLeft ->
		other.map { fromRight -> fromLeft to fromRight }
	}

fun <T, U> Sequence<T>.product(other: Sequence<U>) =
	this.flatMap { fromLeft ->
		other.map { fromRight -> fromLeft to fromRight }
	}

val blankLine = System.lineSeparator() + System.lineSeparator()
val newLine: String = System.lineSeparator()

