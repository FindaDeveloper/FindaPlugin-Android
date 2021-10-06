package kr.co.finda.findaplugin.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElementFactory
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import kr.co.finda.findaplugin.ext.DialogUi
import kr.co.finda.findaplugin.ext.showDialog
import kr.co.finda.findaplugin.model.FindaFlavor
import kr.co.finda.findaplugin.model.ServerConfigFiles
import javax.swing.JPanel

class AddServerConfigAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val directory = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        project.showDialog("Add ServerConfig Property", AddServerConfigDialogUi()) { dialogUi ->
            val serverConfigFiles = ServerConfigFiles.fromConfigModuleDirectory(project, directory)

            WriteCommandAction.runWriteCommandAction(project) {
                serverConfigFiles.flavorAndFile.forEach { (flavor, classElement) ->
                    val propertyName = dialogUi.propertyNameTextField.text
                    val value = dialogUi.valueTextFields[flavor]?.text
                    val fieldText = "public static String $propertyName = \"${value}\";"
                    val fieldElement = PsiElementFactory.getInstance(project)
                        .createFieldFromText(fieldText, null)
                    classElement?.add(fieldElement)
                }
            }
        }
    }

    override fun update(e: AnActionEvent) {
        val directory = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = isConfigModule(directory)
    }

    private fun isConfigModule(directory: VirtualFile?): Boolean {
        return directory?.name?.endsWith("config") ?: false
    }
}

class AddServerConfigDialogUi : DialogUi {
    val propertyNameTextField = JBTextField()

    val valueTextFields: Map<FindaFlavor, JBTextField> =
        FindaFlavor.values()
            .map { flavor -> flavor to JBTextField() }
            .toMap()

    override fun generatePanel(): JPanel {
        val panelBuilder = FormBuilder.createFormBuilder()
        panelBuilder.addLabeledComponent("프로퍼티 이름:", propertyNameTextField)

        valueTextFields.forEach { (flavor, textField) ->
            panelBuilder.addLabeledComponent("$flavor 값:", textField)
        }

        return panelBuilder.panel
    }
}