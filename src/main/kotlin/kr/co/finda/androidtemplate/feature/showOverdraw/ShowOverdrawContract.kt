package kr.co.finda.androidtemplate.feature.showOverdraw

import com.intellij.openapi.project.Project

interface ShowOverdrawContract {

    interface View {
        fun showDialog(project: Project, title: String, description: String)
    }

    interface Presenter {
        fun onShowOverdrawActionPerformed(project: Project)
    }
}