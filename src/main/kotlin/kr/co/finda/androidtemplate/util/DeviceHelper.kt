package kr.co.finda.androidtemplate.util

import com.android.ddmlib.IDevice
import com.android.ddmlib.MultiLineReceiver
import com.android.ddmlib.NullOutputReceiver
import com.intellij.openapi.project.Project
import org.jetbrains.android.sdk.AndroidSdkUtils

interface DeviceHelper {

    fun getDebugLayoutBoundsEnabled(device: IDevice, on: (isEnabled: Boolean) -> Unit)

    fun setDebugLayoutBoundsEnabled(devices: List<IDevice>, isEnabled: Boolean)

    fun getShowOverdrawEnabled(device: IDevice, on: (isEnabled: Boolean) -> Unit)

    fun setShowOverdrawEnabled(devices: List<IDevice>, isEnabled: Boolean)

    fun getDebugDevices(project: Project): List<IDevice>?

    fun clearFindaAppCache(device: IDevice)
}

class DeviceHelperImpl : DeviceHelper {
    override fun getDebugLayoutBoundsEnabled(device: IDevice, on: (isEnabled: Boolean) -> Unit) {
        device.executeShellCommand(
            "getprop debug.layout",
            object : MultiLineReceiver() {
                var cancelled = false

                override fun isCancelled(): Boolean {
                    return cancelled
                }

                override fun processNewLines(lines: Array<out String>?) {
                    val firstLine = lines?.firstOrNull()
                    on(firstLine?.toBoolean() ?: false)
                    cancelled = true
                }
            }
        )
    }

    override fun setDebugLayoutBoundsEnabled(devices: List<IDevice>, isEnabled: Boolean) {
        devices.forEach { device ->
            device.executeShellCommand(
                "setprop debug.layout $isEnabled ; service call activity 1599295570",
                NullOutputReceiver()
            )
        }
    }

    override fun getShowOverdrawEnabled(device: IDevice, on: (isEnabled: Boolean) -> Unit) {
        device.executeShellCommand(
            "getprop debug.hwui.overdraw",
            object: MultiLineReceiver() {
                var cancelled = false

                override fun isCancelled(): Boolean {
                    return cancelled
                }

                override fun processNewLines(lines: Array<out String>?) {
                    val firstLine = lines?.firstOrNull()
                    on(firstLine?.contains("show") == true)
                    cancelled = true
                }
            }
        )
    }

    override fun setShowOverdrawEnabled(devices: List<IDevice>, isEnabled: Boolean) {
        devices.forEach { device ->
            device.executeShellCommand(
                "setprop debug.hwui.overdraw ${if (isEnabled) "show" else "false"}  ; service call activity 1599295570",
                NullOutputReceiver()
            )
        }
    }

    override fun getDebugDevices(project: Project): List<IDevice>? {
        return AndroidSdkUtils.getDebugBridge(project)?.devices?.toList()
    }

    override fun clearFindaAppCache(device: IDevice) {
        device.executeShellCommand(
            "pm clear kr.co.finda.finda.stg",
            NullOutputReceiver()
        )
    }
}