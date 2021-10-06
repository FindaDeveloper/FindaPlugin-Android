package kr.co.finda.androidtemplate.feature.waistUp

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil
import kr.co.finda.androidtemplate.setting.FindaSettingConfigurable

class SetWaistUpStateAction : AnAction("타이머 설정") {

    override fun actionPerformed(e: AnActionEvent) {
        ShowSettingsUtil.getInstance().editConfigurable(e.project, FindaSettingConfigurable())
    }
}