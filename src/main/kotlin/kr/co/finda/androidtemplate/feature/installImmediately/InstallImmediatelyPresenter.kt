package kr.co.finda.androidtemplate.feature.installImmediately

import com.intellij.openapi.project.Project
import kr.co.finda.androidtemplate.util.DeviceHelper

class InstallImmediatelyPresenter(
    private val deviceHelper: DeviceHelper
) : InstallImmediatelyContract.Presenter {

    override fun onInstallPerformed(project: Project) {
        val devices = deviceHelper.getDebugDevices(project)
        deviceHelper.installFindaStgImmediately(devices ?: emptyList())
    }
}