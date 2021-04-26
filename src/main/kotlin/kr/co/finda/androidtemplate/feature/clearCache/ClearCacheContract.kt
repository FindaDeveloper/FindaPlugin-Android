package kr.co.finda.androidtemplate.feature.clearCache

import com.intellij.openapi.project.Project

interface ClearCacheContract {

    interface View {

    }

    interface Presenter {
        fun onClearCachePerformed(project: Project)
    }
}