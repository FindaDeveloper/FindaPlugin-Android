package kr.co.finda.findaplugin.action.createFindaTemplate.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.ui.EnumComboBoxModel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ResourceUtil
import com.intellij.util.ui.FormBuilder
import kr.co.finda.findaplugin.ext.DialogUi
import kr.co.finda.findaplugin.ext.decapitalizeWithUnderBar
import kr.co.finda.findaplugin.ext.showDialog
import kr.co.finda.findaplugin.model.ScreenType
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

            val packageName = getPackageByPath(selectedDirectory.path)
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
        val hasScreenFile = VirtualFileManager.getInstance()
            .findFileByUrl("file://${selectedDirectoryPath}/${name}${screenType.postfix}.kt") != null
        val hasViewModelFile = VirtualFileManager.getInstance()
            .findFileByUrl("file://${selectedDirectoryPath}/${name}ViewModel.kt") != null

        return hasScreenFile || hasViewModelFile
    }

    private fun getPackageByPath(path: String): String {
        val splited = path.split("(java/|kotlin/)".toRegex())
        if (splited.size <= 1) {
            return ""
        }
        return splited[1].replace("/", ".")
    }

    private fun createScreenCodeFile(
        name: String,
        packageName: String,
        screenType: ScreenType,
        selectedDirectory: VirtualFile
    ) {
        val fileNameWithExtension = "${name}${screenType.postfix}.kt"
        val createdFile = selectedDirectory.createChildData(this, fileNameWithExtension)
        val templateContent = getTemplateContentByName("${screenType.postfix}Template")
        val layoutName = getLayoutName(screenType, name)
        val replaced = templateContent.replace("\$NAME$", name)
            .replace("\$PACKAGE$", packageName)
            .replace("\$LAYOUT_NAME$", layoutName)

        VfsUtil.saveText(createdFile, replaced)
    }

    private fun createViewModelFile(
        name: String,
        packageName: String,
        selectedDirectory: VirtualFile
    ) {
        val fileNameWithExtension = "${name}ViewModel.kt"
        val createdFile = selectedDirectory.createChildData(this, fileNameWithExtension)
        val templateContent = getTemplateContentByName("ViewModelTemplate")
        val replaced = templateContent.replace("\$NAME$", name)
            .replace("\$PACKAGE$", packageName)

        VfsUtil.saveText(createdFile, replaced)
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

        val templateName = "LayoutTemplate"
        val createdFile = layoutDirectory.createChildData(this, "${layoutName}.xml")
        val templateContent = getTemplateContentByName(templateName)
        val replaced = templateContent.replace("\$VM_PACKAGE$", "${packageName}.${name}ViewModel")

        VfsUtil.saveText(createdFile, replaced)
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

    private fun getLayoutName(
        screenType: ScreenType,
        name: String
    ): String {
        return "${screenType.name.decapitalize()}_${name.decapitalizeWithUnderBar()}"
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