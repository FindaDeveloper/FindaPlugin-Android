package kr.co.finda.findaplugin.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.ui.components.JBTextField
import com.intellij.util.ResourceUtil
import com.intellij.util.ui.FormBuilder
import kr.co.finda.findaplugin.ext.DialogUi
import kr.co.finda.findaplugin.ext.showDialog
import javax.swing.JPanel

class CreateViewModelTestAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val selectedDirectory = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        project.showDialog("Create ViewModel Test Template", CreateViewModelTestDialogUi()) { dialogUi ->
            val name = dialogUi.nameTextField.text
            val fileNameWithExtension = "${name}ViewModelTest.kt"
            if (hasConflictFileName(name, selectedDirectory.path)) {
                project.showDialog("이미 존재하는 파일입니다", "${selectedDirectory}/${fileNameWithExtension}")
                return@showDialog
            }

            val packageName = getPackageByPath(selectedDirectory.path)
            val createdFile = selectedDirectory.createChildData(this, fileNameWithExtension)
            val templateContent = getTemplateContentByName("ViewModelTestTemplate")
            val replaced = templateContent.replace("\$PACKAGE$", packageName)
                .replace("\$NAME$", name)

            VfsUtil.saveText(createdFile, replaced)
        }
    }

    override fun update(event: AnActionEvent) {
        val selectedDirectory = event.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        event.presentation.isEnabledAndVisible =
            selectedDirectory.path.contains("src/test/(java|kotlin)".toRegex())
    }

    private fun hasConflictFileName(name: String, selectedDirectoryPath: String): Boolean {
        val fileNameWithExtension = "${name}ViewModelTest.kt"
        val file = VirtualFileManager.getInstance()
            .findFileByUrl("file://${selectedDirectoryPath}/${fileNameWithExtension}")
        return file != null
    }

    private fun getPackageByPath(path: String): String {
        val splited = path.split("(java/|kotlin/)".toRegex())
        if (splited.size <= 1) {
            return ""
        }
        return splited[1].replace("/", ".")
    }

    private fun getTemplateContentByName(templateName: String): String {
        val templateFileInputStream = ResourceUtil.getResourceAsStream(
            javaClass.classLoader,
            "templates",
            "${templateName}.txt"
        )
        return templateFileInputStream.bufferedReader()
            .readLines()
            .reduce { acc, s -> "${acc}\n${s}" }
    }
}

class CreateViewModelTestDialogUi : DialogUi {
    val nameTextField = JBTextField()
    val panel = FormBuilder.createFormBuilder()
        .addLabeledComponent("ViewModel 이름:", nameTextField)
        .panel

    override fun generatePanel(): JPanel = panel
}