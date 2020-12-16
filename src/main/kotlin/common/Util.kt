package common

fun <T, U> Iterable<T>.cartesianProduct(other: Iterable<U>) =
	this.flatMap { fromLeft ->
		other.map { fromRight -> fromLeft to fromRight }
	}

fun <T, U> Sequence<T>.cartesianProduct(other: Sequence<U>) =
	this.flatMap { fromLeft ->
		other.map { fromRight -> fromLeft to fromRight }
	}


fun Iterable<Int>.product() =
	this.reduce { a, b -> a * b }

fun Iterable<Long>.product() =
	this.reduce { a, b -> a * b }

val blankLine = System.lineSeparator() + System.lineSeparator()
val newLine: String = System.lineSeparator()

