package com.github.kimdohun0104.findatemplatepluginandroid.services

import com.github.kimdohun0104.findatemplatepluginandroid.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
