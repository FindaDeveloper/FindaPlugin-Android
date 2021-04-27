package kr.co.finda.androidtemplate.feature.setting

import com.intellij.openapi.options.Configurable
import kr.co.finda.androidtemplate.feature.waistUp.WaistUpService
import kr.co.finda.androidtemplate.feature.waistUp.WaistUpStateComponent
import javax.swing.JComponent

class FindaSettingConfigurable : Configurable {

    private lateinit var component: FindaSettingComponent

    private val state: WaistUpStateComponent by lazy {
        WaistUpStateComponent.createInstance()
    }

    override fun createComponent(): JComponent {
        component = FindaSettingComponent(state)
        return component.panel
    }

    override fun isModified(): Boolean {
        return state.isEnabled != component.waistUpCheckBox.isSelected
                || state.dialogDisplayTime != component.displayTimeTextField.text.toLong()
                || state.dialogWaitTime != component.waitTimeTextField.text.toLong()
    }

    override fun apply() {
        state.isEnabled = component.waistUpCheckBox.isSelected
        state.dialogDisplayTime = component.displayTimeTextField.text.toLong()
        state.dialogWaitTime = component.waitTimeTextField.text.toLong()

        WaistUpService.setNotificationEnable(state.isEnabled)
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return component.displayTimeTextField
    }

    override fun reset() {
        component.waistUpCheckBox.isSelected = state.isEnabled
        component.displayTimeTextField.text = state.dialogDisplayTime.toString()
        component.waitTimeTextField.text = state.dialogWaitTime.toString()
    }

    override fun getDisplayName(): String {
        return "Finda Settings"
    }
}