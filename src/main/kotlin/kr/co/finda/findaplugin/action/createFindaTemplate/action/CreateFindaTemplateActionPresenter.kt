package kr.co.finda.findaplugin.action.createFindaTemplate.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.findaplugin.util.ActionRouter
import kr.co.finda.findaplugin.type.PluginError

class CreateFindaTemplateActionPresenter(
    private val view: CreateFindaTemplateActionContract.View,
    private val actionRouter: ActionRouter
) : CreateFindaTemplateActionContract.Presenter {

    override fun onCreateFindaTemplateActionPerformed(
        project: Project,
        selectedDirectory: VirtualFile?
    ) {
        if (selectedDirectory == null) {
            view.showErrorDialog(project, PluginError.FT101)
            return
        }

        if (!isValidSelectedDirectoryPath(selectedDirectory.path)) {
            view.showErrorDialog(project, PluginError.FT102)
            return
        }

        view.showCreateFindaTemplateDialog(project, selectedDirectory)
    }

    override fun onActionUpdate(event: AnActionEvent, selectedDirectory: VirtualFile?) {
        event.presentation.isEnabledAndVisible =
            if (selectedDirectory != null) actionRouter.isCreateFindaTemplateEnable(selectedDirectory.path)
            else false
    }

    private fun isValidSelectedDirectoryPath(path: String): Boolean {
        return actionRouter.isCreateFindaTemplateEnable(path)
    }
}