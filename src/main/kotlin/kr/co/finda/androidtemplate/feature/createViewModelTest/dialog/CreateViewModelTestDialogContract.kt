package kr.co.finda.androidtemplate.feature.createViewModelTest.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.androidtemplate.type.PluginError

interface CreateViewModelTestDialogContract {

    interface View {
        fun showConflictNameDialog(
            project: Project,
            pluginError: PluginError,
            conflictedFileName: String
        )
    }

    interface Presenter {
        fun onCreateViewModelTest(
            project: Project,
            name: String,
            selectedDirectory: VirtualFile
        )
    }
}