package kr.co.finda.androidtemplate.feature.showLayoutBound.action

import com.android.ddmlib.MultiLineReceiver
import com.android.ddmlib.NullOutputReceiver
import com.intellij.openapi.project.Project
import org.jetbrains.android.sdk.AndroidSdkUtils

class ShowLayoutBoundsActionPresenter(
    private val view: ShowLayoutBoundsActionContract.View
) : ShowLayoutBoundsActionContract.Presenter {

    override fun onShowLayoutBoundsActionPerformed(project: Project) {
        val devices = AndroidSdkUtils.getDebugBridge(project)?.devices

        if (devices == null) {
            view.showDialog(project, "연결된 디바이스가 없습니다", "디바이스 연결 상태를 확인해주세요")
            return
        }

        devices.forEach { device ->
            device.executeShellCommand(
                "getprop debug.layout",
                object : MultiLineReceiver() {
                    var cancelled = false

                    override fun isCancelled(): Boolean {
                        return cancelled
                    }

                    override fun processNewLines(lines: Array<out String>?) {
                        lines?.firstOrNull()?.let { first ->
                            device.executeShellCommand(
                                "setprop debug.layout ${!first.toBoolean()} ; service call activity 1599295570",
                                NullOutputReceiver()
                            )
                        }
                        cancelled = true
                    }

                }
            )
        }
    }
}