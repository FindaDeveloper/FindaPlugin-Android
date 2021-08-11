package kr.co.finda.androidtemplate.feature.installImmediately

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import kr.co.finda.androidtemplate.util.DeviceHelperImpl

class InstallImmediatelyAction : AnAction(), InstallImmediatelyContract.View {

    private val presenter: InstallImmediatelyContract.Presenter by lazy {
        InstallImmediatelyPresenter(DeviceHelperImpl())
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        presenter.onInstallPerformed(project)
    }
}