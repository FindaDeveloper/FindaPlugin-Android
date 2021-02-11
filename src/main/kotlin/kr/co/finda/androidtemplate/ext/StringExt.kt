package kr.co.finda.androidtemplate.ext

fun String.decapitalizeWithUnderBar(): String {
    return this.split(Regex("^[A-Z]$"))
        .joinToString("_") { it.decapitalize() }
}