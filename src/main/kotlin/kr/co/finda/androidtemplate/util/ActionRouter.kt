package kr.co.finda.androidtemplate.util

import com.intellij.openapi.vfs.VirtualFile

interface ActionRouter {

    fun isCreateFindaTemplateEnable(path: String): Boolean

    fun isCreateViewModelTestTemplateEnable(path: String): Boolean

    fun isAddServerConfigEnable(directory: VirtualFile): Boolean
}

class ActionRouterImpl : ActionRouter {
    override fun isCreateFindaTemplateEnable(path: String): Boolean {
        return path.contains("src/main/(java|kotlin)".toRegex())
    }

    override fun isCreateViewModelTestTemplateEnable(path: String): Boolean {
        return path.contains("src/test/(java|kotlin)".toRegex())
    }

    override fun isAddServerConfigEnable(directory: VirtualFile): Boolean {
        return directory.children.toList().any {
            it.name.contains("build.gradle")
        }
    }
}