package kr.co.finda.androidtemplate.feature.setting

import com.intellij.openapi.options.Configurable
import kr.co.finda.androidtemplate.feature.waistUp.WaistUpService
import javax.swing.JComponent

class FindaSettingConfigurable : Configurable {

    private lateinit var component: FindaSettingComponent

    override fun createComponent(): JComponent {
        component = FindaSettingComponent(WaistUpService.state)
        return component.panel
    }

    override fun isModified(): Boolean {
        val state = WaistUpService.state
        return state.isEnabled != component.waistUpCheckBox.isSelected
                || state.hideDelay != component.hideDelayTextField.text.toLong()
                || state.waitDelay != component.waitDelayTextField.text.toLong()
    }

    override fun apply() {
        val state = WaistUpService.state
        state.isEnabled = component.waistUpCheckBox.isSelected
        state.hideDelay = component.hideDelayTextField.text.toLong()
        state.waitDelay = component.waitDelayTextField.text.toLong()

        WaistUpService.setNotificationEnable(state.isEnabled)
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return component.hideDelayTextField
    }

    override fun reset() {
        val state = WaistUpService.state
        component.waistUpCheckBox.isSelected = state.isEnabled
        component.hideDelayTextField.text = state.hideDelay.toString()
        component.waitDelayTextField.text = state.waitDelay.toString()
    }

    override fun getDisplayName(): String {
        return "Finda Settings"
    }
}