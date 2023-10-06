package kr.co.finda.androidtemplate.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.EnumComboBoxModel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import kr.co.finda.androidtemplate.ext.DialogUi
import kr.co.finda.androidtemplate.ext.showDialog
import kr.co.finda.androidtemplate.model.ScreenType
import kr.co.finda.androidtemplate.model.Template
import kr.co.finda.androidtemplate.model.TemplateParam
import kr.co.finda.androidtemplate.util.FileUtil
import kr.co.finda.androidtemplate.util.TemplateUtil
import javax.swing.JPanel

class CreateFindaTemplateAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val selectedDirectory = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        project.showDialog("Create Finda Template", CreateFindaTemplateDialogUi()) { dialogUi ->
            val name = dialogUi.nameTextField.text

            if (hasConflictFileName(name, selectedDirectory.path)) {
                project.showDialog("중복되는 이름의 파일이 존재합니다")
                return@showDialog
            }

            val packageName = FileUtil.getPackageByPath(selectedDirectory.path)
            createTemplateFile(name, packageName, ScreenType.ACTIVITY, selectedDirectory)
            createTemplateFile(name, packageName, ScreenType.EES, selectedDirectory)
            createTemplateFile(name, packageName, ScreenType.SCREEN, selectedDirectory)
            createTemplateFile(name, packageName, ScreenType.VIEW_MODEL, selectedDirectory)
        }
    }

    override fun update(event: AnActionEvent) {
        val selectedDirectory = event.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        event.presentation.isEnabledAndVisible =
            selectedDirectory.path.contains("src/main/(java|kotlin)".toRegex())
    }

    private fun hasConflictFileName(
        name: String,
        selectedDirectoryPath: String
    ): Boolean {
        val templateList = listOf(
                ScreenType.ACTIVITY,
                ScreenType.EES,
                ScreenType.SCREEN,
                ScreenType.VIEW_MODEL
        )
        templateList.forEach {screenType ->
            val isConflictFileName = FileUtil.hasConflictFileName(
                    "${name}${screenType.postfix}.kt",
                    selectedDirectoryPath
            )
            if(isConflictFileName) return true
        }
        return false
    }

    private fun createTemplateFile(
        name: String,
        packageName: String,
        screenType: ScreenType,
        selectedDirectory: VirtualFile
    ) {
        val fileNameWithExtension = "${name}${screenType.postfix}.kt"

        TemplateUtil.createFileWithTemplate(
            screenType.template,
            fileNameWithExtension,
            selectedDirectory,
            TemplateParam.NAME to name,
            TemplateParam.PACKAGE to packageName,
        )
    }
}

class CreateFindaTemplateDialogUi : DialogUi {

    private val screenTypeModel = EnumComboBoxModel(ScreenType::class.java)

    val nameTextField = JBTextField()
    private val screenTypeComboBox = ComboBox(screenTypeModel)
    private val panel = FormBuilder.createFormBuilder()
        .addLabeledComponent("화면 이름:", nameTextField)
        .addLabeledComponent("화면 종류:", screenTypeComboBox)
        .panel

    override fun generatePanel(): JPanel = panel
}