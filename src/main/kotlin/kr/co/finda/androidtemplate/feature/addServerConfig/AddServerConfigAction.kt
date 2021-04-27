package kr.co.finda.androidtemplate.feature.addServerConfig

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import kr.co.finda.androidtemplate.feature.addServerConfig.dialog.AddServerConfigDialog
import kr.co.finda.androidtemplate.type.ServerConfig
import kr.co.finda.androidtemplate.util.FileHelperImpl
import kr.co.finda.androidtemplate.util.ReplacerImpl

class AddServerConfigAction : AnAction(), AddServerConfigContract.View {

    private val presenter: AddServerConfigContract.Presenter by lazy {
        AddServerConfigPresenter(this, FileHelperImpl(ReplacerImpl()))
    }

    override fun actionPerformed(e: AnActionEvent) {
        val selectedDirectory = e.getData(CommonDataKeys.VIRTUAL_FILE)!!
        presenter.onActionPerformed(e.project!!, selectedDirectory)
    }

    override fun update(e: AnActionEvent) {
        val selectedDirectory = e.getData(CommonDataKeys.VIRTUAL_FILE)
        presenter.onActionUpdate(e, selectedDirectory)
    }

    override fun showAddServerConfigDialog(serverConfig: ServerConfig) {
        AddServerConfigDialog(serverConfig).show()
    }
}