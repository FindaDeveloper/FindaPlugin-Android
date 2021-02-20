package kr.co.finda.androidtemplate.feature.setting

import com.intellij.ui.components.CheckBox
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.layout.panel
import com.intellij.util.ui.FormBuilder
import kr.co.finda.androidtemplate.type.WaistUpState
import javax.swing.JPanel

class FindaSettingComponent(
    state: WaistUpState
) {

    val hideDelayTextField: JBTextField = JBTextField()
    val waitDelayTextField: JBTextField = JBTextField()
    val waistUpCheckBox: JBCheckBox = JBCheckBox("허리펴! 알림 활성화")

    val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponent(JBLabel("허리펴! 설정"))
        .addLabeledComponent("숨김 시간", hideDelayTextField)
        .addLabeledComponent("다음 허리펴! 까지 시간", waitDelayTextField)
        .addComponent(waistUpCheckBox)
        .panel

    init {
        hideDelayTextField.text = state.hideDelay.toString()
        waitDelayTextField.text = state.waitDelay.toString()
        waistUpCheckBox.isSelected = state.isEnabled
    }
}