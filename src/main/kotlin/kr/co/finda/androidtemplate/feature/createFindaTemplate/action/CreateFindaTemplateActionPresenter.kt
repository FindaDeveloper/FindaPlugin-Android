package kr.co.finda.androidtemplate.feature.createFindaTemplate.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.androidtemplate.PluginError

class CreateFindaTemplateActionPresenter(
    private val view: CreateFindaTemplateActionContract.View
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

    override fun onActionUpdate(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = event.project != null
    }

    private fun isValidSelectedDirectoryPath(path: String): Boolean {
        return path.contains("^.*src/main/(java|kotlin).*$".toRegex())
    }
}