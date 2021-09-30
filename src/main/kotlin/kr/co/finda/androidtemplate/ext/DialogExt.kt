package kr.co.finda.androidtemplate.ext

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import icons.Icons

fun Project.showDialog(
    title: String,
    message: String = ""
) {
    Messages.showMessageDialog(this, message, title, Icons.FindaLogo)
}