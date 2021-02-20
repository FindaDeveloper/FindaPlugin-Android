package kr.co.finda.androidtemplate.feature.setting

import com.intellij.openapi.options.Configurable
import kr.co.finda.androidtemplate.feature.waistUp.WaistUpService
import javax.swing.JComponent

class FindaSettingConfigurable : Configurable {

    private lateinit var findaSettingComponent: FindaSettingComponent

    override fun createComponent(): JComponent {
        findaSettingComponent = FindaSettingComponent(WaistUpService.state)
        return findaSettingComponent.panel
    }

    override fun isModified(): Boolean {
        println("isModified")
        val state = WaistUpService.state
        return state.isEnabled != findaSettingComponent.waistUpCheckBox.isSelected
                || state.hideDelay != findaSettingComponent.hideDelayTextField.text.toLong()
                || state.waitDelay != findaSettingComponent.waitDelayTextField.text.toLong()
    }

    override fun apply() {
        val state = WaistUpService.state
        state.isEnabled = findaSettingComponent.waistUpCheckBox.isSelected
        state.hideDelay = findaSettingComponent.hideDelayTextField.text.toLong()
        state.waitDelay = findaSettingComponent.waitDelayTextField.text.toLong()
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return findaSettingComponent.hideDelayTextField
    }

    override fun reset() {
        val state = WaistUpService.state
        findaSettingComponent.waistUpCheckBox.isSelected = state.isEnabled
        findaSettingComponent.hideDelayTextField.text = state.hideDelay.toString()
        findaSettingComponent.waitDelayTextField.text = state.waitDelay.toString()
    }

    override fun getDisplayName(): String {
        return "Finda Settings"
    }
}