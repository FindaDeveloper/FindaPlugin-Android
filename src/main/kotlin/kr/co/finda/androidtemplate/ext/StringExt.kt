package kr.co.finda.androidtemplate.ext

fun String.decapitalizeWithUnderBar(): String {
    return this.split(Regex("^[A-Z]$"))
        .joinToString("_") { it.decapitalize() }
}

fun String.replaceAll(oldValue: String, newValue: String?): String {
    return this.replace(oldValue.toRegex(), newValue ?: "")
}