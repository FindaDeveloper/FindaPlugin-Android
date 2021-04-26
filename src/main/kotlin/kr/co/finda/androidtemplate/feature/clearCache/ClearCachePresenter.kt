package kr.co.finda.androidtemplate.feature.clearCache

import com.intellij.openapi.project.Project
import kr.co.finda.androidtemplate.util.DeviceHelper

class ClearCachePresenter(
    private val view: ClearCacheContract.View,
    private val deviceHelper: DeviceHelper
) : ClearCacheContract.Presenter {

    override fun onClearCachePerformed(project: Project) {
        val devices = deviceHelper.getDebugDevices(project)
        deviceHelper.clearFindaAppCache(devices!![0])
    }
}