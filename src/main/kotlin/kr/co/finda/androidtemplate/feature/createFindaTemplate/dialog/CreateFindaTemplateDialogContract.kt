package kr.co.finda.androidtemplate.feature.createFindaTemplate.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.androidtemplate.PluginError
import kr.co.finda.androidtemplate.models.ScreenType

interface CreateFindaTemplateDialogContract {

    interface View {
        fun showErrorDialog(
            project: Project,
            pluginError: PluginError
        )

        fun showConflictNameDialog(
            project: Project,
            pluginError: PluginError,
            conflictedFileName: String
        )
    }

    interface Presenter {
        fun onCreateFindaTemplate(
            project: Project,
            name: String,
            screenType: ScreenType,
            selectedDirectory: VirtualFile
        )
    }
}