package kr.co.finda.androidtemplate.feature.createFindaTemplate.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.androidtemplate.type.PluginError
import kr.co.finda.androidtemplate.util.FileHelper
import kr.co.finda.androidtemplate.util.Replacements
import kr.co.finda.androidtemplate.ext.decapitalizeWithUnderBar
import kr.co.finda.androidtemplate.type.FileExtension
import kr.co.finda.androidtemplate.type.ScreenType

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
        createLayoutFile(project, name, packageName, screenType, selectedDirectory)
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
            Replacements(
                name = name,
                packageName = packageName,
                layoutName = getLayoutName(screenType, name)
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
            Replacements(
                name = name,
                packageName = packageName
            )
        )
    }

    private fun createLayoutFile(
        project: Project,
        name: String,
        packageName: String,
        screenType: ScreenType,
        selectedDirectory: VirtualFile
    ) {
        val layoutName = getLayoutName(screenType, name)
        val layoutDirectory = fileHelper.getLayoutDirectory(selectedDirectory.path)
        if (layoutDirectory == null) {
            view.showErrorDialog(project, PluginError.FT104)
            return
        }

        val templateName = "LayoutTemplate"

        fileHelper.createFileWithTemplate(
            layoutDirectory,
            layoutName,
            FileExtension.XML,
            templateName,
            Replacements(
                viewModelPackage = "${packageName}.${name}ViewModel"
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