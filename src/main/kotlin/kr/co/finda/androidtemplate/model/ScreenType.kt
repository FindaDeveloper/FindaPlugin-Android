package kr.co.finda.androidtemplate.model

enum class ScreenType(
    val postfix: String,
    val template: Template
) {
    ACTIVITY("Activity", Template.ACTIVITY),
    FRAGMENT("Fragment", Template.FRAGMENT),
    BOTTOM_SHEET("BottomSheet", Template.BOTTOM_SHEET),
}