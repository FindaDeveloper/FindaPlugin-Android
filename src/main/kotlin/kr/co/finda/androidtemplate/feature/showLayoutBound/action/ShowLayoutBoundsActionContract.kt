package kr.co.finda.androidtemplate.feature.showLayoutBound.action

import com.intellij.openapi.project.Project

interface ShowLayoutBoundsActionContract {

    interface View {
        fun showDialog(project: Project, title: String, message: String)
    }

    interface Presenter {
        fun onShowLayoutBoundsActionPerformed(project: Project)
    }
}