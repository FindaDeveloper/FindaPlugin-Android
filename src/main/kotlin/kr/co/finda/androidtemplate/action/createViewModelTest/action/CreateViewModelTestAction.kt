package kr.co.finda.androidtemplate.action.createViewModelTest.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.androidtemplate.ext.showDialog
import kr.co.finda.androidtemplate.action.createViewModelTest.dialog.CreateViewModelTestDialog
import kr.co.finda.androidtemplate.type.PluginError
import kr.co.finda.androidtemplate.util.ActionRouterImpl

class CreateViewModelTestAction :
    AnAction(), CreateViewModelTestActionContract.View {

    private val presenter: CreateViewModelTestActionContract.Presenter by lazy {
        CreateViewModelTestActionPresenter(this, ActionRouterImpl())
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project!!
        val selectedDirectory = e.getData(CommonDataKeys.VIRTUAL_FILE)

        presenter.onCreateViewModelTestTemplateActionPerformed(project, selectedDirectory)
    }

    override fun update(event: AnActionEvent) {
        val selectedDirectory = event.getData(CommonDataKeys.VIRTUAL_FILE)
        presenter.onActionUpdate(event, selectedDirectory)
    }

    override fun showErrorDialog(project: Project, pluginError: PluginError) {
        project.showDialog(pluginError.title, pluginError.message)
    }

    override fun showCreateViewModelTestTemplateDialog(project: Project, selectedDirectory: VirtualFile) {
        CreateViewModelTestDialog(project, selectedDirectory).show()
    }
}