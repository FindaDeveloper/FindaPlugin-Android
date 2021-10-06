package kr.co.finda.findaplugin.action.createViewModelTest.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.findaplugin.type.PluginError

interface CreateViewModelTestActionContract {

    interface View {

        fun showErrorDialog(project: Project, pluginError: PluginError)

        fun showCreateViewModelTestTemplateDialog(project: Project, selectedDirectory: VirtualFile)
    }

    interface Presenter {

        fun onActionUpdate(
            event: AnActionEvent,
            selectedDirectory: VirtualFile?
        )

        fun onCreateViewModelTestTemplateActionPerformed(
            project: Project,
            selectedDirectory: VirtualFile?
        )
    }
}