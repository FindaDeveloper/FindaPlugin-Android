package kr.co.finda.findaplugin.ext

fun String.decapitalizeWithUnderBar(): String {
    return this.split("(?=[A-Z])".toRegex())
        .joinToString("_") { it.decapitalize() }
        .replaceFirst("_", "")
}