package kr.co.finda.androidtemplate.feature.createFindaTemplate.action

import com.intellij.find.impl.livePreview.ReplacementView
import com.intellij.ide.IdeTooltipManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.popup.BalloonBuilder
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.ui.PositionTracker
import icons.Icons
import kr.co.finda.androidtemplate.model.PluginError
import kr.co.finda.androidtemplate.feature.createFindaTemplate.dialog.CreateFindaTemplateDialog
import kr.co.finda.androidtemplate.feature.waistUp.WaistUpNotification

class CreateFindaTemplateAction : AnAction(), CreateFindaTemplateActionContract.View {

    private val presenter: CreateFindaTemplateActionPresenter by lazy {
        CreateFindaTemplateActionPresenter(this)
    }

    override fun actionPerformed(e: AnActionEvent) {
        val currentProject = e.project
        val selectedDirectory = e.getData(CommonDataKeys.VIRTUAL_FILE)

        presenter.onCreateFindaTemplateActionPerformed(currentProject!!, selectedDirectory)
    }

    override fun update(event: AnActionEvent) {
        presenter.onActionUpdate(event)
    }

    override fun showErrorDialog(
        project: Project,
        error: PluginError
    ) {
        Messages.showMessageDialog(
            project,
            error.message,
            error.title,
            Icons.FindaLogo
        )
    }

    override fun showCreateFindaTemplateDialog(
        project: Project,
        selectedDirectory: VirtualFile
    ) {
        CreateFindaTemplateDialog(project, selectedDirectory).show()
    }
}