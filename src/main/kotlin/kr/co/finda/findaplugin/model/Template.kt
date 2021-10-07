package kr.co.finda.findaplugin.model

enum class Template(
    val templateFileName: String
) {
    ACTIVITY("ActivityTemplate.txt"),
    BOTTOM_SHEET("BottomSheetTemplate.txt"),
    FRAGMENT("FragmentTemplate.txt"),
    LAYOUT("LayoutTemplate.txt"),
    VIEW_MODEL("ViewModelTemplate.txt"),
    VIEW_MODEL_TEST("ViewModelTestTemplate.txt")
}