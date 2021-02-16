package kr.co.finda.androidtemplate.feature.createFindaTemplate.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.androidtemplate.model.PluginError
import kr.co.finda.androidtemplate.common.FileHelper
import kr.co.finda.androidtemplate.ext.decapitalizeWithUnderBar
import kr.co.finda.androidtemplate.model.FileExtension
import kr.co.finda.androidtemplate.model.ScreenType

class CreateFindaTemplateDialogPresenter(
    private val view: CreateFindaTemplateDialogContract.View,
    private val fileHelper: FileHelper
) : CreateFindaTemplateDialogContract.Presenter {

    override fun onCreateFindaTemplate(
        project: Project,
        name: String,
        screenType: ScreenType,
        selectedDirectory: VirtualFile
    ) {
        getConflictedFileName(name, screenType, selectedDirectory.path)?.let {
            view.showConflictNameDialog(project, PluginError.FT103, it)
            return
        }

        val packageName = fileHelper.getPackageNameByPath(selectedDirectory.path)
        createUiCodeFile(name, packageName, screenType, selectedDirectory)
        createViewModelFile(name, packageName, selectedDirectory)
        createLayoutFile(project, name, packageName, screenType)
    }

    private fun getConflictedFileName(
        name: String,
        screenType: ScreenType,
        selectedDirectoryPath: String
    ): String? {
        val isSameCodeFileExist = fileHelper.isFileExistWithPath(
            "${selectedDirectoryPath}/${name}${screenType.name}.kt"
        )
        val isSameViewModelFileExist = fileHelper.isFileExistWithPath(
            "${selectedDirectoryPath}/${name}ViewModel.kt"
        )

        return when {
            isSameCodeFileExist -> "${name}${screenType.name}.kt"
            isSameViewModelFileExist -> "${name}ViewModel.kt"
            else -> null
        }
    }

    private fun createUiCodeFile(
        name: String,
        packageName: String,
        screenType: ScreenType,
        selectedDirectory: VirtualFile
    ) {
        val templateName = "${screenType.name}Template"

        fileHelper.createFileWithTemplate(
            selectedDirectory,
            "${name}${screenType.name}",
            FileExtension.Kotlin,
            templateName,
            mapOf(
                "name" to name,
                "package" to packageName,
                "layoutname" to getLayoutName(screenType, name)
            )
        )
    }

    private fun createViewModelFile(
        name: String,
        packageName: String,
        selectedDirectory: VirtualFile
    ) {
        val templateName = "ViewModelTemplate"

        fileHelper.createFileWithTemplate(
            selectedDirectory,
            "${name}ViewModel",
            FileExtension.Kotlin,
            templateName,
            mapOf("name" to name, "package" to packageName)
        )
    }

    private fun createLayoutFile(
        project: Project,
        name: String,
        packageName: String,
        screenType: ScreenType
    ) {
        val layoutDirectory = fileHelper.getLayoutDirectory(project.basePath!!)

        val templateName = "LayoutTemplate"

        fileHelper.createFileWithTemplate(
            layoutDirectory!!,
            getLayoutName(screenType, name),
            FileExtension.XML,
            templateName,
            mapOf(
                "vmpackage" to "${packageName}.${name}ViewModel"
            )
        )
    }

    private fun getLayoutName(
        screenType: ScreenType,
        name: String
    ): String {
        return "${screenType.name.decapitalize()}_${name.decapitalizeWithUnderBar()}"
    }
}