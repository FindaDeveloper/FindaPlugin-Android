package kr.co.finda.androidtemplate.dialogs

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.EnumComboBoxModel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.layout.panel
import kr.co.finda.androidtemplate.models.ScreenType
import javax.swing.JComponent

class CreateFindaTemplateDialog : DialogWrapper(true) {

    private val screenTypeModel = EnumComboBoxModel(ScreenType::class.java)
    private lateinit var nameTextField: JBTextField

    init {
        init()
        title = "Create Finda Template"
    }

    override fun createCenterPanel(): JComponent {
        return panel {

            row("화면 이름") {
                nameTextField = textField({""}, {}).component
            }

            row("화면 종류:") {
                comboBox(
                    screenTypeModel,
                    { screenTypeModel.selectedItem },
                    { screenTypeModel.setSelectedItem(it) }
                )
            }
        }
    }

    override fun doOKAction() {
        val selectedScreenType = screenTypeModel.selectedItem
        val screenName = nameTextField.text

        println("selected: ${selectedScreenType}")
        println("value: ${screenName}")
        super.doOKAction()
    }
}