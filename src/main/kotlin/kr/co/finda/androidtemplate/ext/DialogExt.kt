package kr.co.finda.androidtemplate.ext

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import icons.Icons
import javax.swing.JComponent
import javax.swing.JPanel

fun Project.showDialog(
    title: String,
    message: String = ""
) {
    Messages.showMessageDialog(this, message, title, Icons.FindaLogo)
}

fun <T : DialogUi> Project.showDialog(
    title: String,
    dialogUi: T,
    onClickOk: (ui: T) -> Unit
) {
    CustomDialogWrapper(this, title, dialogUi, onClickOk).showDialog()
}


class CustomDialogWrapper<T : DialogUi>(
    private val project: Project,
    private val title: String,
    private val dialogUi: T,
    private val onClickOk: (ui: T) -> Unit
) {

    fun showDialog() {
        val dialog = object : DialogWrapper(project) {

            init {
                init()
                title = this@CustomDialogWrapper.title
            }

            override fun createCenterPanel(): JComponent =
                dialogUi.generatePanel()

            override fun doOKAction() {
                onClickOk(dialogUi)
                super.doOKAction()
            }
        }

        dialog.show()
    }
}

interface DialogUi {
    fun generatePanel(): JPanel
}