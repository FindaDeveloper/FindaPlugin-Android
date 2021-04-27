package kr.co.finda.androidtemplate.feature.addServerConfig

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.androidtemplate.util.ActionRouter
import kr.co.finda.androidtemplate.util.FileHelper

class AddServerConfigPresenter(
    private val view: AddServerConfigContract.View,
    private val fileHelper: FileHelper,
    private val actionRouter: ActionRouter
) : AddServerConfigContract.Presenter {

    override fun onActionPerformed(project: Project, selectedDirectory: VirtualFile) {
        val serverConfig = fileHelper.getServerConfig(project, selectedDirectory)
        view.showAddServerConfigDialog(project, serverConfig)
    }

    override fun onActionUpdate(e: AnActionEvent, selectedDirectory: VirtualFile?) {
        e.presentation.isEnabledAndVisible =
            if (selectedDirectory == null) false
            else actionRouter.isAddServerConfigEnable(selectedDirectory)
    }
}