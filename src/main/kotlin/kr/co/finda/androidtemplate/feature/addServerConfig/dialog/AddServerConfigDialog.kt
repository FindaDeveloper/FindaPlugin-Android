package kr.co.finda.androidtemplate.feature.addServerConfig.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import kr.co.finda.androidtemplate.feature.createFindaTemplate.dialog.CreateFindaTemplateDialogContract
import kr.co.finda.androidtemplate.type.ServerConfig
import javax.swing.JComponent

class AddServerConfigDialog(
    private val project: Project,
    private val serverConfig: ServerConfig
) : DialogWrapper(true), AddServerConfigContract.View {

    private val presenter: AddServerConfigContract.Presenter by lazy {
        AddServerConfigPresenter(this)
    }

    private val propertyNameTextField = JBTextField()
    private val devTextField = JBTextField()
    private val stgTextField = JBTextField()
    private val uatTextField = JBTextField()
    private val prdTextField = JBTextField()
    private val panel = FormBuilder.createFormBuilder()
        .addLabeledComponent("프로퍼티 이름:", propertyNameTextField)
        .addLabeledComponent("dev 값:", devTextField)
        .addLabeledComponent("stg 값:", stgTextField)
        .addLabeledComponent("uat 값:", uatTextField)
        .addLabeledComponent("prd 값:", prdTextField)
        .panel

    init {
        init()
        title = "Add ServerConfig Property"
    }

    override fun createCenterPanel(): JComponent? {
        return panel
    }

    override fun doOKAction() {
        presenter.onAddServerConfig(
            project = project,
            propertyName = propertyNameTextField.text,
            devValue = devTextField.text,
            stgValue = stgTextField.text,
            uatValue = uatTextField.text,
            prdValue = prdTextField.text,
            serverConfig = serverConfig
        )
        super.doOKAction()
    }
}