package kr.co.finda.androidtemplate.feature.addServerConfig.dialog

import com.intellij.openapi.project.Project
import kr.co.finda.androidtemplate.type.ServerConfig

interface AddServerConfigContract {

    interface View {

    }

    interface Presenter {
        fun onAddServerConfig(
            project: Project,
            propertyName: String,
            devValue: String,
            stgValue: String,
            uatValue: String,
            prdValue: String,
            serverConfig: ServerConfig
        )
    }
}