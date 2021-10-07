package kr.co.finda.androidtemplate.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.EnumComboBoxModel
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import kr.co.finda.androidtemplate.ext.DialogUi
import kr.co.finda.androidtemplate.ext.showDialog
import kr.co.finda.androidtemplate.model.FindaBuildType
import kr.co.finda.androidtemplate.util.CommandLineUtil
import javax.swing.JPanel

class ResignAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        project.showDialog("Re-sign finda aab", ResignDialogUi()) { dialogUi ->
            val password = dialogUi.passwordTextField.text
            val buildType = dialogUi.buildTypeModel.selectedItem

            val command = COMMAND_RESIGN_AAB.format(password, buildType.name.toLowerCase())
            val commandOutput = CommandLineUtil.executeCommand(project, command)

            if (commandOutput.contains("error")) {
                project.showDialog("에러가 발생했습니다", commandOutput)
            } else {
                project.showDialog("재서명에 성공했습니다")
            }
        }
    }

    companion object {
        private const val COMMAND_RESIGN_AAB = "jarsigner -verbose -sigalg SHA256withRSA " +
                "-digestalg SHA-256 -keystore app/keystore/finda.jks -storepass %s " +
                "appsolid_unsigned_app-stg-release.aab key0"
    }
}

class ResignDialogUi : DialogUi {
    val buildTypeModel = EnumComboBoxModel(FindaBuildType::class.java)

    val buildTypeComboBox = ComboBox(buildTypeModel)
    val passwordTextField = JBTextField()
    val panel = FormBuilder.createFormBuilder()
        .addLabeledComponent("Flavor 선택", buildTypeComboBox)
        .addLabeledComponent("비밀번호", passwordTextField)
        .addComponent(JBTextArea("OK버튼을 클릭한 후 한동안 에러가 뜨지 않으면 성공적으로 재서명을 실행한 것입니다."))
        .addComponent(JBTextArea("재서명이 완료될 때까지 해당 다이얼로그 동작이 멈출 수 있습니다."))
        .panel

    override fun generatePanel(): JPanel {
        return panel
    }
}