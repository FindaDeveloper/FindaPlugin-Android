package kr.co.finda.androidtemplate.feature.createFindaTemplate.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.androidtemplate.type.PluginError

interface CreateFindaTemplateActionContract {

    interface View {
        fun showErrorDialog(
            project: Project,
            error: PluginError
        )

        fun showCreateFindaTemplateDialog(
            project: Project,
            selectedDirectory: VirtualFile
        )
    }

    interface Presenter {
        fun onCreateFindaTemplateActionPerformed(
            project: Project,
            selectedDirectory: VirtualFile?
        )

        fun onActionUpdate(event: AnActionEvent)
    }
}