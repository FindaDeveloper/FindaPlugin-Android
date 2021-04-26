package kr.co.finda.androidtemplate.feature.clearCache

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import kr.co.finda.androidtemplate.util.DeviceHelperImpl

class ClearCacheAction : AnAction(), ClearCacheContract.View {

    private val presenter: ClearCacheContract.Presenter by lazy {
        ClearCachePresenter(this, DeviceHelperImpl())
    }

    override fun actionPerformed(e: AnActionEvent) {
        presenter.onClearCachePerformed(e.project!!)
    }
}