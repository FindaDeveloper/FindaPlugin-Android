package kr.co.finda.androidtemplate.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages
import com.intellij.pom.Navigatable
import kr.co.finda.androidtemplate.dialogs.CreateFindaTemplateDialog

class CreateFindaTemplateAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val currentProject = e.project
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)

        if (virtualFile != null) {
            CreateFindaTemplateDialog(virtualFile).showAndGet()
        } else {
            Messages.showMessageDialog(
                currentProject,
                "문제가 지속된다면 담당자에게 연락해주세요",
                "알 수 없는 문제가 발생했습니다",
                Messages.getInformationIcon()
            )
        }
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }
}