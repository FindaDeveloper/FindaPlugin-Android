package kr.co.finda.androidtemplate.feature.addServerConfig

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kr.co.finda.androidtemplate.type.ServerConfig

interface AddServerConfigContract {

    interface View {
        fun showAddServerConfigDialog(serverConfig: ServerConfig)
    }

    interface Presenter {
        fun onActionUpdate(e: AnActionEvent, selectedDirectory: VirtualFile?)

        fun onActionPerformed(project: Project, selectedDirectory: VirtualFile)
    }
}