package kr.co.finda.androidtemplate.models

import kr.co.finda.androidtemplate.ext.decapitalizeWithUnderBar

sealed class GeneratedFileInfo(
    open val name: String,
    val codeFilePostfix: String,
    val layoutFilePrefix: String
) {

    val codeFileName: String
        get() = "${name}${codeFilePostfix}.kt"

    val viewModelFileName: String
        get() = "${name}ViewModel.kt"

    val layoutFileName: String
        get() = "${layoutFilePrefix}_${name.decapitalizeWithUnderBar()}.xml"

    data class Activity(override val name: String) : GeneratedFileInfo(
        name = name,
        codeFilePostfix = "Activity",
        layoutFilePrefix = "activity"
    )

    data class Fragment(override val name: String) : GeneratedFileInfo(
        name = name,
        codeFilePostfix = "Fragment",
        layoutFilePrefix = "fragment"
    )

    data class BottomSheet(override val name: String) : GeneratedFileInfo(
        name = name,
        codeFilePostfix = "BottomSheet",
        layoutFilePrefix = "bottom_sheet"
    )
}