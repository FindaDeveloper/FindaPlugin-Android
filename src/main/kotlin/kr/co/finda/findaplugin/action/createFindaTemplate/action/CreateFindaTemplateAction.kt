package kr.co.finda.findaplugin.action.createFindaTemplate.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.findaplugin.ext.showDialog
import kr.co.finda.findaplugin.action.createFindaTemplate.dialog.CreateFindaTemplateDialog
import kr.co.finda.findaplugin.type.PluginError
import kr.co.finda.findaplugin.util.ActionRouterImpl

class CreateFindaTemplateAction :
    AnAction(), CreateFindaTemplateActionContract.View {

    private val presenter: CreateFindaTemplateActionContract.Presenter by lazy {
        CreateFindaTemplateActionPresenter(this, ActionRouterImpl())
    }

    override fun actionPerformed(e: AnActionEvent) {
        val currentProject = e.project
        val selectedDirectory = e.getData(CommonDataKeys.VIRTUAL_FILE)

        presenter.onCreateFindaTemplateActionPerformed(currentProject!!, selectedDirectory)
    }

    override fun update(event: AnActionEvent) {
        val selectedDirectory = event.getData(CommonDataKeys.VIRTUAL_FILE)
        presenter.onActionUpdate(event, selectedDirectory)
    }

    override fun showErrorDialog(
        project: Project,
        error: PluginError
    ) {
        project.showDialog(error.title, error.message)
    }

    override fun showCreateFindaTemplateDialog(
        project: Project,
        selectedDirectory: VirtualFile
    ) {
        CreateFindaTemplateDialog(project, selectedDirectory).show()
    }
}