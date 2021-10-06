package kr.co.finda.androidtemplate.feature.resign

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.showOkNoDialog
import com.intellij.ui.EnumComboBoxModel
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import kr.co.finda.androidtemplate.type.AabType
import javax.swing.JComponent

class ResignDialog(
    private val project: Project
) : DialogWrapper(true), ResignContract.View {

    private val presenter: ResignContract.Presenter by lazy {
        ResignPresenter(this)
    }

    private val aabTypeModel = EnumComboBoxModel(AabType::class.java)

    private val aabTypeComboBox = ComboBox(aabTypeModel)
    private val passwordTextField = JBTextField()
    private val panel = FormBuilder.createFormBuilder()
        .addLabeledComponent("Flavor 선택", aabTypeComboBox)
        .addLabeledComponent("비밀번호", passwordTextField)
        .addComponent(JBTextArea("OK버튼을 클릭한 후 한동안 에러가 뜨지 않으면 성공적으로 재서명을 실행한 것입니다."))
        .addComponent(JBTextArea("재서명이 완료될 때까지 해당 다이얼로그 동작이 멈출 수 있습니다."))
        .panel

    init {
        init()
        title = "Re-sign finda aab"
    }

    override fun createCenterPanel(): JComponent? {
        return panel
    }

    override fun doOKAction() {
        presenter.onResign(
            project = project,
            aabType = aabTypeModel.selectedItem,
            password = passwordTextField.text
        )
    }

    override fun showErrorDialog(message: String) {
        showOkNoDialog("에러가 발생했습니다", message, project)
    }

    override fun onSuccess() {
        showOkNoDialog("재서명에 성공했습니다", "", project)
    }
}