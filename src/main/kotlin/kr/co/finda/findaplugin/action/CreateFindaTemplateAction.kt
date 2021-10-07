package kr.co.finda.findaplugin.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.ui.EnumComboBoxModel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import kr.co.finda.findaplugin.ext.DialogUi
import kr.co.finda.findaplugin.ext.decapitalizeWithUnderBar
import kr.co.finda.findaplugin.ext.showDialog
import kr.co.finda.findaplugin.model.ScreenType
import kr.co.finda.findaplugin.model.Template
import kr.co.finda.findaplugin.model.TemplateParam
import kr.co.finda.findaplugin.util.FileUtil
import kr.co.finda.findaplugin.util.TemplateUtil
import javax.swing.JPanel

class CreateFindaTemplateAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val selectedDirectory = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        project.showDialog("Create Finda Template", CreateFindaTemplateDialogUi()) { dialogUi ->
            val name = dialogUi.nameTextField.text
            val screenType = dialogUi.screenTypeModel.selectedItem

            if (hasConflictFileName(name, screenType, selectedDirectory.path)) {
                project.showDialog("중복되는 ${screenType.postfix} 혹은 ViewModel 파일이 존재합니다")
                return@showDialog
            }

            val packageName = FileUtil.getPackageByPath(selectedDirectory.path)
            createScreenCodeFile(name, packageName, screenType, selectedDirectory)
            createViewModelFile(name, packageName, selectedDirectory)
            createLayoutFile(project, name, packageName, screenType, selectedDirectory)
        }
    }

    override fun update(event: AnActionEvent) {
        val selectedDirectory = event.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        event.presentation.isEnabledAndVisible =
            selectedDirectory.path.contains("src/main/(java|kotlin)".toRegex())
    }

    private fun hasConflictFileName(
        name: String,
        screenType: ScreenType,
        selectedDirectoryPath: String
    ): Boolean {
        val hasScreenFile = FileUtil.hasConflictFileName(
            "${name}${screenType.postfix}.kt",
            selectedDirectoryPath
        )
        val hasViewModelFile = FileUtil.hasConflictFileName(
            "${name}ViewModel.kt",
            selectedDirectoryPath
        )
        val layoutDirectory = getLayoutDirectory(selectedDirectoryPath)?.path ?: ""
        val hasLayoutFile = FileUtil.hasConflictFileName(getLayoutName(screenType, name), layoutDirectory)

        return hasScreenFile || hasViewModelFile || hasLayoutFile
    }

    private fun createScreenCodeFile(
        name: String,
        packageName: String,
        screenType: ScreenType,
        selectedDirectory: VirtualFile
    ) {
        val fileNameWithExtension = "${name}${screenType.postfix}.kt"

        val layoutName = getLayoutName(screenType, name)

        TemplateUtil.createFileWithTemplate(
            screenType.template,
            fileNameWithExtension,
            selectedDirectory,
            TemplateParam.NAME to name,
            TemplateParam.PACKAGE to packageName,
            TemplateParam.LAYOUT to layoutName
        )
    }

    private fun createViewModelFile(
        name: String,
        packageName: String,
        selectedDirectory: VirtualFile
    ) {
        val fileNameWithExtension = "${name}ViewModel.kt"

        TemplateUtil.createFileWithTemplate(
            Template.VIEW_MODEL,
            fileNameWithExtension,
            selectedDirectory,
            TemplateParam.NAME to name,
            TemplateParam.PACKAGE to packageName
        )
    }

    private fun createLayoutFile(
        project: Project,
        name: String,
        packageName: String,
        screenType: ScreenType,
        selectedDirectory: VirtualFile
    ) {
        val layoutName = getLayoutName(screenType, name)
        val layoutDirectory = getLayoutDirectory(selectedDirectory.path)
        if (layoutDirectory == null) {
            project.showDialog("레이아웃 폴더를 찾을 수 없습니다")
            return
        }

        val fileNameWithExtension = "${layoutName}.xml"

        TemplateUtil.createFileWithTemplate(
            Template.LAYOUT,
            fileNameWithExtension,
            selectedDirectory,
            TemplateParam.VIEW_MODEL_PACKAGE to "${packageName}.${name}ViewModel",
            TemplateParam.PACKAGE to packageName,
            TemplateParam.LAYOUT to layoutName
        )
    }

    private fun getLayoutName(
        screenType: ScreenType,
        name: String
    ): String {
        return "${screenType.name.toLowerCase()}_${name.decapitalizeWithUnderBar()}"
    }

    private fun getLayoutDirectory(selectedDirectoryPath: String): VirtualFile? {
        val mainPath = selectedDirectoryPath.split("src/main")[0]
        val layoutPath = "${mainPath}/src/main/res/layout"
        return VirtualFileManager.getInstance()
            .findFileByUrl("file://${layoutPath}")
    }
}

class CreateFindaTemplateDialogUi : DialogUi {

    val screenTypeModel = EnumComboBoxModel(ScreenType::class.java)

    val nameTextField = JBTextField()
    val screenTypeComboBox = ComboBox(screenTypeModel)
    val panel = FormBuilder.createFormBuilder()
        .addLabeledComponent("화면 이름:", nameTextField)
        .addLabeledComponent("화면 종류:", screenTypeComboBox)
        .panel

    override fun generatePanel(): JPanel = panel
}