package kr.co.finda.findaplugin

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import kr.co.finda.findaplugin.service.WaistUpService

class FindaProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<WaistUpService>()
    }
}