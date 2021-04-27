package kr.co.finda.androidtemplate.feature.addServerConfig

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.VirtualFile

interface AddServerConfigContract {

    interface View {

    }

    interface Presenter {
        fun onActionUpdate(e: AnActionEvent, selectedDirectory: VirtualFile?)
    }
}