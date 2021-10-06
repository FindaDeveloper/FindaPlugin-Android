package kr.co.finda.androidtemplate.service

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

class FindaProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<WaistUpService>()
    }
}