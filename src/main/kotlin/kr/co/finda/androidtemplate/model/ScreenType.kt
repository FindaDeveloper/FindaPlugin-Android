package kr.co.finda.androidtemplate.model

enum class ScreenType(
    val postfix: String,
    val template: Template
) {
    ACTIVITY("Activity", Template.ACTIVITY),
    EES("Ees", Template.EES),
    SCREEN("Screen", Template.SCREEN),
    VIEW_MODEL("ViewModel", Template.VIEW_MODEL),
}