package kr.co.finda.androidtemplate.feature.createViewModelTest.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.androidtemplate.model.ActionRouter
import kr.co.finda.androidtemplate.type.PluginError

class CreateViewModelTestActionPresenter(
    private val view: CreateViewModelTestActionContract.View,
    private val actionRouter: ActionRouter
) : CreateViewModelTestActionContract.Presenter {

    override fun onActionUpdate(
        event: AnActionEvent,
        selectedDirectory: VirtualFile?
    ) {
        event.presentation.isEnabledAndVisible =
            if (selectedDirectory != null) actionRouter.isCreateViewModelTestTemplateEnable(selectedDirectory.path)
            else false
    }

    override fun onCreateViewModelTestTemplateActionPerformed(
        project: Project,
        selectedDirectory: VirtualFile?
    ) {
        if (selectedDirectory == null) {
            view.showErrorDialog(project, PluginError.VTT101)
            return
        }

        if (!actionRouter.isCreateViewModelTestTemplateEnable(selectedDirectory.path)) {
            view.showErrorDialog(project, PluginError.VTT102)
            return
        }

        view.showCreateViewModelTestTemplateDialog(project, selectedDirectory)
    }
}