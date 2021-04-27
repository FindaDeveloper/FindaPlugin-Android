package kr.co.finda.androidtemplate.feature.addServerConfig.dialog

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.util.ui.FormBuilder
import kr.co.finda.androidtemplate.feature.createFindaTemplate.dialog.CreateFindaTemplateDialogContract
import kr.co.finda.androidtemplate.type.ServerConfig
import javax.swing.JComponent

class AddServerConfigDialog(
    private val serverConfig: ServerConfig
) : DialogWrapper(true), AddServerConfigContract.View {

    private val presenter: AddServerConfigContract.Presenter by lazy {
        AddServerConfigPresenter(this)
    }

    private val panel = FormBuilder.createFormBuilder()
//        .addLabeledComponent("화면 이름:", nameTextField)
//        .addLabeledComponent("화면 종류:", screenTypeComboBox)
        .panel


    override fun createCenterPanel(): JComponent? {
        return panel
    }
}