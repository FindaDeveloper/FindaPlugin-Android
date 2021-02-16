package kr.co.finda.androidtemplate.ext

fun String.decapitalizeWithUnderBar(): String {
    return this.split("(?=[A-Z])".toRegex())
        .joinToString("_") { it.decapitalize() }
        .replaceFirst("_", "")
}

fun String.replaceAll(oldValue: String, newValue: String?): String {
    return this.replace(oldValue.toRegex(), newValue ?: "")
}