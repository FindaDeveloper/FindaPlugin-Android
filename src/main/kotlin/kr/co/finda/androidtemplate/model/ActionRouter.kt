package kr.co.finda.androidtemplate.model

interface ActionRouter {

    fun isCreateFindaTemplateEnable(path: String): Boolean

    fun isCreateViewModelTestTemplateEnable(path: String): Boolean
}

class ActionRouterImpl : ActionRouter {
    override fun isCreateFindaTemplateEnable(path: String): Boolean {
        return path.contains("src/main/(java|kotlin)".toRegex())
    }

    override fun isCreateViewModelTestTemplateEnable(path: String): Boolean {
        return path.contains("src/test/(java|kotlin)".toRegex())
    }
}