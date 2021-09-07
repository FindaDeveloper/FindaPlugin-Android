package kr.co.finda.androidtemplate.feature.resign

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ResignAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        ResignDialog(e.project!!).show()
    }
}