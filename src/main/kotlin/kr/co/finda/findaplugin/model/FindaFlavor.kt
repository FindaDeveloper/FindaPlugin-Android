package kr.co.finda.findaplugin.model

enum class FindaFlavor(
    val path: String
) {
    DEV("dev"),
    STG("stg"),
    UAT("uat"),
    PRD("prd")
}