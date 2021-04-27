package kr.co.finda.androidtemplate.feature.waistUp

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.options.newEditor.SettingsDialog
import kr.co.finda.androidtemplate.feature.setting.FindaSettingConfigurable

class SetWaistUpStateAction : AnAction("타이머 설정") {

    override fun actionPerformed(e: AnActionEvent) {
        ShowSettingsUtil.getInstance().editConfigurable(e.project, FindaSettingConfigurable())
    }
}