package kr.co.finda.findaplugin.model

enum class ScreenType(
    val postfix: String
) {
    ACTIVITY("Activity"),
    FRAGMENT("Fragment"),
    BOTTOM_SHEET("BottomSheet"),
}