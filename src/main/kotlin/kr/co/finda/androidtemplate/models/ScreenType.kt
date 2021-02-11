package kr.co.finda.androidtemplate.models

enum class ScreenType(
    val codeFileTemplate: String
) {
    Activity(
        codeFileTemplate = "ActivityTemplate.txt"
    ),
    Fragment(
        codeFileTemplate = "FragmentTemplate.txt"
    ),

    Dialog(
        codeFileTemplate = "DialogTemplate.txt"
    ),
    BottomSheet(
        codeFileTemplate = "BottomSheetTemplate.txt"
    );

    fun getGeneratedFileInfo(name: String): GeneratedFileInfo {
        return when(this) {
            Activity -> GeneratedFileInfo.Activity(name)
            Fragment -> GeneratedFileInfo.Fragment(name)
            BottomSheet -> GeneratedFileInfo.BottomSheet(name)
            Dialog -> GeneratedFileInfo.Dialog(name)
        }
    }
}