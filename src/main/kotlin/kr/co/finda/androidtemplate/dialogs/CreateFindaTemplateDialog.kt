package kr.co.finda.androidtemplate.dialogs

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.EnumComboBoxModel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.layout.panel
import com.intellij.util.ResourceUtil
import kr.co.finda.androidtemplate.models.ScreenType
import kr.co.finda.androidtemplate.models.GeneratedFileInfo
import kr.co.finda.androidtemplate.models.TemplateInfo
import java.io.File
import javax.swing.JComponent

class CreateFindaTemplateDialog(
    private val virtualFile: VirtualFile
) : DialogWrapper(true) {

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
        val templateInfo = TemplateInfo(screenTypeModel.selectedItem)
        val generatedFileInfo = screenTypeModel.selectedItem.getGeneratedFileInfo(nameTextField.text)

        val codeFile = virtualFile.createChildData(this, generatedFileInfo.codeFileName)
        VfsUtil.saveText(codeFile, templateInfo.codeTemplateContent)

        super.doOKAction()
    }
}