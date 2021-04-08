package kr.co.finda.androidtemplate.feature.showLayoutBound.action

import com.android.ddmlib.MultiLineReceiver
import com.android.ddmlib.NullOutputReceiver
import com.intellij.openapi.project.Project
import kr.co.finda.androidtemplate.util.DeviceHelper
import org.jetbrains.android.sdk.AndroidSdkUtils

class ShowLayoutBoundsActionPresenter(
    private val view: ShowLayoutBoundsActionContract.View,
    private val deviceHelper: DeviceHelper
) : ShowLayoutBoundsActionContract.Presenter {

    override fun onShowLayoutBoundsActionPerformed(project: Project) {
        val devices = deviceHelper.getDebugDevices(project)

        if (devices.isNullOrEmpty()) {
            view.showDialog(project, "연결된 디바이스가 없습니다", "디바이스 연결 상태를 확인해주세요")
            return
        }

        deviceHelper.getDebugLayoutBoundsEnabled(devices[0]) { isEnabled ->
            deviceHelper.setDebugLayoutBoundsEnabled(devices, !isEnabled)
        }
    }
}