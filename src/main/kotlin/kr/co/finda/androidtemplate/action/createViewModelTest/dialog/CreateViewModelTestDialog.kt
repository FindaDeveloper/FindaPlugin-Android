package kr.co.finda.androidtemplate.action.createViewModelTest.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import kr.co.finda.androidtemplate.ext.showDialog
import kr.co.finda.androidtemplate.util.FileHelperImpl
import kr.co.finda.androidtemplate.util.ReplacerImpl
import kr.co.finda.androidtemplate.type.PluginError
import javax.swing.JComponent

class CreateViewModelTestDialog(
    private val project: Project,
    private val selectedDirectory: VirtualFile
) : DialogWrapper(true), CreateViewModelTestDialogContract.View {

    private val presenter: CreateViewModelTestDialogContract.Presenter by lazy {
        CreateViewModelTestDialogPresenter(this, FileHelperImpl(ReplacerImpl()))
    }

    private val nameTextField = JBTextField()
    private val panel = FormBuilder.createFormBuilder()
        .addLabeledComponent("ViewModel 이름:", nameTextField)
        .panel

    init {
        init()
        title = "Create ViewModel Test Template"
    }

    override fun createCenterPanel(): JComponent {
        return panel
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
        project.showDialog(
            title = pluginError.title,
            message = "${pluginError.message}\n${conflictedFileName}"
        )
    }
}