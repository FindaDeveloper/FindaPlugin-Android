package kr.co.finda.androidtemplate.feature.showOverdraw

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import kr.co.finda.androidtemplate.ext.showMessageDialog
import kr.co.finda.androidtemplate.util.DeviceHelperImpl

class ShowOverdrawAction : AnAction(), ShowOverdrawContract.View {

    private val presenter: ShowOverdrawContract.Presenter by lazy {
        ShowOverdrawPresenter(this, DeviceHelperImpl())
    }

    override fun actionPerformed(e: AnActionEvent) {
        presenter.onShowOverdrawActionPerformed(e.project!!)
    }

    override fun showDialog(project: Project, title: String, description: String) {
        project.showMessageDialog(title, description)
    }
}