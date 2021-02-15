package kr.co.finda.androidtemplate.dialogs

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.ui.EnumComboBoxModel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.layout.panel
import kr.co.finda.androidtemplate.ext.replaceAll
import kr.co.finda.androidtemplate.models.GeneratedFileInfo
import kr.co.finda.androidtemplate.models.ScreenType
import kr.co.finda.androidtemplate.models.TemplateInfo
import javax.swing.JComponent

class CreateFindaTemplateDialog(
    private val project: Project,
    private val virtualFile: VirtualFile
) : DialogWrapper(true) {

    private val screenTypeModel = EnumComboBoxModel(ScreenType::class.java)

    private lateinit var nameTextField: JBTextField
    val name: String
        get() = nameTextField.text

    init {
        init()
        title = "Create Finda Template"
    }

    override fun createCenterPanel(): JComponent {
        return panel {

            row("화면 이름") {
                nameTextField = textField({ "" }, {}).component
            }

            row("화면 종류:") {
                comboBox(
                    screenTypeModel,
                    { screenTypeModel.selectedItem },
                    { screenTypeModel.setSelectedItem(it) }
                )
            }
        }
    }

    override fun doOKAction() {
        val templateInfo = TemplateInfo(screenTypeModel.selectedItem)
        val generatedFileInfo = screenTypeModel.selectedItem.getGeneratedFileInfo(name)

        val packageName = getPackageNameByPath(virtualFile.path)

        createCodeFile(packageName, generatedFileInfo, templateInfo.codeTemplateContent)
        createViewModelFile(packageName, generatedFileInfo, templateInfo.viewModelTemplateContent)
        createLayoutFile(packageName, generatedFileInfo, templateInfo.layoutTemplateContent)

        super.doOKAction()
    }

    private fun getPackageNameByPath(path: String): String {
        return try {
            path.split("java/")[1]
                .replaceAll("/", ".")
        } catch (e: IndexOutOfBoundsException) {
            ""
        }
    }

    private fun createCodeFile(
        packageName: String,
        generatedFileInfo: GeneratedFileInfo,
        templateContent: String
    ) {
        val codeFile = virtualFile.createChildData(this, "${generatedFileInfo.codeFileName}.kt")

        val content = templateContent.replaceAll("@NAME@", name)
            .replaceAll("@PACKAGE@", packageName)
            .replaceAll("@LAYOUT_NAME@", generatedFileInfo.layoutFileName)

        VfsUtil.saveText(codeFile, content)
    }

    private fun createViewModelFile(
        packageName: String,
        generatedFileInfo: GeneratedFileInfo,
        templateContent: String
    ) {
        val viewModelFile = virtualFile.createChildData(this, "${generatedFileInfo.viewModelFileName}.kt")

        val content = templateContent.replaceAll("@NAME@", name)
            .replaceAll("@PACKAGE@", packageName)

        VfsUtil.saveText(viewModelFile, content)
    }

    private fun createLayoutFile(
        packageName: String,
        generatedFileInfo: GeneratedFileInfo,
        templateContent: String
    ) {
        val layoutDirectory = VirtualFileManager.getInstance()
            .findFileByUrl("file://${project.basePath}/src/main/res/layout")
        val layoutFile = layoutDirectory?.createChildData(this, "${generatedFileInfo.layoutFileName}.xml")

        val content = templateContent
            .replaceAll("@VM_PACKAGE@", "${packageName}.${generatedFileInfo.viewModelFileName}")

        layoutFile?.let {
            VfsUtil.saveText(layoutFile, content)
        }
    }
}