package kr.co.finda.androidtemplate.feature.addServerConfig

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import kr.co.finda.androidtemplate.util.FileHelperImpl
import kr.co.finda.androidtemplate.util.ReplacerImpl

class AddServerConfigAction : AnAction(), AddServerConfigContract.View {

    private val presenter: AddServerConfigContract.Presenter by lazy {
        AddServerConfigPresenter(this, FileHelperImpl(ReplacerImpl()))
    }

    override fun actionPerformed(e: AnActionEvent) {

    }

    override fun update(e: AnActionEvent) {
        val selectedDirectory = e.getData(CommonDataKeys.VIRTUAL_FILE)
        presenter.onActionUpdate(e, selectedDirectory)
    }
}