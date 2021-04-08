package kr.co.finda.androidtemplate.ext

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import icons.Icons
import kr.co.finda.androidtemplate.type.PluginError

fun Project.showErrorDialog(pluginError: PluginError) {
    Messages.showMessageDialog(
        this,
        pluginError.message,
        pluginError.title,
        Icons.FindaLogo
    )
}

fun Project.showMessageDialog(title: String, message: String) {
    Messages.showMessageDialog(
        this,
        message,
        title,
        Icons.FindaLogo
    )
}