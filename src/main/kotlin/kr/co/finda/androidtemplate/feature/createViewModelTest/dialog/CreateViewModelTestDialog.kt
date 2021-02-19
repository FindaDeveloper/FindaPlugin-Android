package kr.co.finda.androidtemplate.feature.createViewModelTest.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBTextField
import com.intellij.ui.layout.panel
import icons.Icons
import kr.co.finda.androidtemplate.model.FileHelperImpl
import kr.co.finda.androidtemplate.model.ReplacerImpl
import kr.co.finda.androidtemplate.type.PluginError
import javax.swing.JComponent

class CreateViewModelTestDialog(
    private val project: Project,
    private val selectedDirectory: VirtualFile
) : DialogWrapper(true), CreateViewModelTestDialogContract.View {

    private val presenter: CreateViewModelTestDialogContract.Presenter by lazy {
        CreateViewModelTestDialogPresenter(this, FileHelperImpl(ReplacerImpl()))
    }

    private lateinit var nameTextField: JBTextField

    init {
        init()
        title = "Create ViewModel Test Template"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("ViewModel 이름:") {
                nameTextField = textField({ "" }, {}).component
            }
        }
    }

    override fun doOKAction() {
        presenter.onCreateViewModelTest(
            project = project,
            name = nameTextField.text,
            selectedDirectory = selectedDirectory
        )
        super.doOKAction()
    }

    override fun showConflictNameDialog(project: Project, pluginError: PluginError, conflictedFileName: String) {
        Messages.showMessageDialog(
            project,
            "${pluginError.message}\n${conflictedFileName}",
            pluginError.title,
            Icons.FindaLogo
        )
    }
}