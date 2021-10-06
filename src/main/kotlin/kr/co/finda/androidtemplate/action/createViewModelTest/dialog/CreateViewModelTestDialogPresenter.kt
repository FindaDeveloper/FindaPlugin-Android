package kr.co.finda.androidtemplate.action.createViewModelTest.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.androidtemplate.util.FileHelper
import kr.co.finda.androidtemplate.util.Replacements
import kr.co.finda.androidtemplate.type.FileExtension
import kr.co.finda.androidtemplate.type.PluginError

class CreateViewModelTestDialogPresenter(
    private val view: CreateViewModelTestDialogContract.View,
    private val fileHelper: FileHelper
) : CreateViewModelTestDialogContract.Presenter {

    override fun onCreateViewModelTest(
        project: Project,
        name: String,
        selectedDirectory: VirtualFile
    ) {
        getConflictedFileName(name, selectedDirectory.path)?.let {
            view.showConflictNameDialog(project, PluginError.VTT103, it)
            return
        }

        val packageName = fileHelper.getPackageNameByPath(selectedDirectory.path)
        createViewModelTest(selectedDirectory, name, packageName)
    }

    private fun getConflictedFileName(
        name: String,
        selectedDirectoryPath: String
    ): String? {
        val fileNameWithExtension = "${name}ViewModelTest.kt"
        val isFileExist = fileHelper.isFileExistWithPath(
            "${selectedDirectoryPath}/${fileNameWithExtension}"
        )

        return if (isFileExist) {
            fileNameWithExtension
        } else {
            null
        }
    }

    private fun createViewModelTest(
        selectedDirectory: VirtualFile,
        name: String,
        packageName: String
    ) {
        fileHelper.createFileWithTemplate(
            selectedDirectory,
            "${name}ViewModelTest",
            FileExtension.Kotlin,
            "ViewModelTestTemplate",
            Replacements(
                packageName = packageName,
                name = name
            )
        )
    }
}