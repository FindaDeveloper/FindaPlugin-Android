package kr.co.finda.androidtemplate.feature.showLayoutBound.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import org.jetbrains.android.sdk.AndroidSdkUtils

class ShowLayoutBoundsAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        AndroidSdkUtils.getDebugBridge(e.project!!)
    }
}