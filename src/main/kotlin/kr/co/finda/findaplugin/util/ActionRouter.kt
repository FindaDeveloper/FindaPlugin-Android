package kr.co.finda.findaplugin.util

interface ActionRouter {

    fun isCreateFindaTemplateEnable(path: String): Boolean
}

class ActionRouterImpl : ActionRouter {
    override fun isCreateFindaTemplateEnable(path: String): Boolean {
        return path.contains("src/main/(java|kotlin)".toRegex())
    }
}