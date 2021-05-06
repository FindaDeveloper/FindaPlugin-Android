package kr.co.finda.androidtemplate.feature.showOverdraw

import com.intellij.openapi.project.Project
import kr.co.finda.androidtemplate.util.DeviceHelper

class ShowOverdrawPresenter(
    private val view: ShowOverdrawContract.View,
    private val deviceHelper: DeviceHelper
) : ShowOverdrawContract.Presenter {
    override fun onShowOverdrawActionPerformed(project: Project) {
        val devices = deviceHelper.getDebugDevices(project)

        if (devices.isNullOrEmpty()) {
            view.showDialog(project, "연결된 디바이스가 없습니다", "디바이스 연결 상태를 확인해주세요")
            return
        }

        deviceHelper.getShowOverdrawEnabled(devices[0]) { prevEnabled ->
            deviceHelper.setShowOverdrawEnabled(devices, !prevEnabled)
        }
    }
}