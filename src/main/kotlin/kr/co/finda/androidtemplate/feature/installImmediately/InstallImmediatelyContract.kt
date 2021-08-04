package kr.co.finda.androidtemplate.feature.installImmediately

import com.intellij.openapi.project.Project

interface InstallImmediatelyContract {

    interface View {

    }

    interface Presenter {
        fun onInstallPerformed(project: Project)
    }
}