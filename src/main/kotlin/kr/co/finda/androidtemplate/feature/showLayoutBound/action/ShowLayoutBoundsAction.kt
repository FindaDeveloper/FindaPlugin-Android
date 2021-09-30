package kr.co.finda.androidtemplate.feature.showLayoutBound.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import kr.co.finda.androidtemplate.ext.showDialog
import kr.co.finda.androidtemplate.util.DeviceHelperImpl

class ShowLayoutBoundsAction : AnAction(), ShowLayoutBoundsActionContract.View {

    private val presenter: ShowLayoutBoundsActionContract.Presenter by lazy {
        ShowLayoutBoundsActionPresenter(this, DeviceHelperImpl())
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project!!
        presenter.onShowLayoutBoundsActionPerformed(project)
    }

    override fun showDialog(project: Project, title: String, message: String) {
        project.showDialog(title, message)
    }
}