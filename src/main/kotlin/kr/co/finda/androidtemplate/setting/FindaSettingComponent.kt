package kr.co.finda.androidtemplate.setting

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import kr.co.finda.androidtemplate.feature.waistUp.WaistUpStateComponent
import javax.swing.JPanel

class FindaSettingComponent(
    state: WaistUpStateComponent
) {

    val displayTimeTextField: JBTextField = JBTextField()
    val waitTimeTextField: JBTextField = JBTextField()
    val waistUpCheckBox: JBCheckBox = JBCheckBox("허리펴! 알림 활성화")

    val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponent(JBLabel("허리펴! 설정"))
        .addLabeledComponent("다이얼로그 노출 시간", displayTimeTextField)
        .addLabeledComponent("다음 노출까지 시간", waitTimeTextField)
        .addComponent(waistUpCheckBox)
        .panel

    init {
        displayTimeTextField.text = state.dialogDisplayTime.toString()
        waitTimeTextField.text = state.dialogWaitTime.toString()
        waistUpCheckBox.isSelected = state.isEnabled
    }
}