package kr.co.finda.androidtemplate.feature.createFindaTemplate.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.EnumComboBoxModel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.layout.panel
import icons.Icons
import kr.co.finda.androidtemplate.type.PluginError
import kr.co.finda.androidtemplate.model.FileHelperImpl
import kr.co.finda.androidtemplate.model.ReplacerImpl
import kr.co.finda.androidtemplate.type.ScreenType
import javax.swing.JComponent

class CreateFindaTemplateDialog(
    private val project: Project,
    private val selectedDirectory: VirtualFile
) : DialogWrapper(true), CreateFindaTemplateDialogContract.View {

    private val presenter: CreateFindaTemplateDialogContract.Presenter by lazy {
        CreateFindaTemplateDialogPresenter(
            view = this,
            fileHelper = FileHelperImpl(ReplacerImpl())
        )
    }

    private val screenTypeModel = EnumComboBoxModel(ScreenType::class.java)
    private lateinit var nameTextField: JBTextField

    init {
        init()
        title = "Create Finda Template"
    }

    override fun createCenterPanel(): JComponent {
        return panel {

            row("화면 이름:") {
                nameTextField = textField({ "" }, {}).component
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
        presenter.onCreateFindaTemplate(
            project = project,
            name = nameTextField.text,
            screenType = screenTypeModel.selectedItem,
            selectedDirectory = selectedDirectory
        )
        super.doOKAction()
    }

    override fun showErrorDialog(
        project: Project,
        pluginError: PluginError
    ) {
        Messages.showMessageDialog(
            project,
            pluginError.message,
            pluginError.title,
            Icons.FindaLogo
        )
    }

    override fun showConflictNameDialog(
        project: Project,
        pluginError: PluginError,
        conflictedFileName: String
    ) {
        Messages.showMessageDialog(
            project,
            "${pluginError.message}\n${conflictedFileName}",
            pluginError.title,
            Icons.FindaLogo
        )
    }
}