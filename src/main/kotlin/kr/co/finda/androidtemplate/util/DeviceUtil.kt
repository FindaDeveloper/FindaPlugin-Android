package kr.co.finda.androidtemplate.util

import com.android.ddmlib.IDevice
import com.android.ddmlib.MultiLineReceiver
import com.android.ddmlib.NullOutputReceiver
import com.intellij.openapi.project.Project
import org.jetbrains.android.sdk.AndroidSdkUtils

object DeviceUtil {

    private const val COMMAND_GET_OVERDRAW_PROPERTY = "getprop debug.hwui.overdraw"
    private const val COMMAND_SET_OVERDRAW_PROPERTY =
        "setprop debug.hwui.overdraw %s ; service call activity 1599295570"

    private const val COMMAND_GET_LAYOUT_BOUND_PROPERTY = "getprop debug.layout"
    private const val COMMAND_SET_LAYOUT_BOUND_PROPERTY = "setprop debug.layout %d ; service call activity 1599295570"

    private const val COMMAND_CLEAR_CACHE = "pm clear kr.co.finda.finda.stg"

    fun getDebugDevices(project: Project): List<IDevice> {
        return AndroidSdkUtils.getDebugBridge(project)?.devices?.toList() ?: emptyList()
    }

    fun toggleShowOverdrawSetting(devices: List<IDevice>) {
        devices[0].executeShellCommand(COMMAND_GET_OVERDRAW_PROPERTY, generateMultilineReceiver {
            val firstLine = it.firstOrNull()
            val isShowing = firstLine?.contains("show") == true
            devices.forEach { device ->
                device.executeShellCommand(
                    COMMAND_SET_OVERDRAW_PROPERTY.format(if (isShowing) "false" else "show"),
                    NullOutputReceiver()
                )
            }
        })
    }

    fun toggleShowLayoutBoundSetting(devices: List<IDevice>) {
        devices[0].executeShellCommand(COMMAND_GET_LAYOUT_BOUND_PROPERTY, generateMultilineReceiver {
            val firstLine = it.firstOrNull()
            val isShowing = firstLine?.toBoolean() ?: false
            devices.forEach { device ->
                device.executeShellCommand(
                    COMMAND_SET_LAYOUT_BOUND_PROPERTY.format((!isShowing).toString()),
                    NullOutputReceiver()
                )
            }
        })
    }

    fun clearFindaStgAppCache(device: IDevice) {
        device.executeShellCommand(COMMAND_CLEAR_CACHE, NullOutputReceiver())
    }

    private fun generateMultilineReceiver(
        onProcessNewLine: (lines: Array<out String>) -> Unit
    ) = object : MultiLineReceiver() {
        var cancelled = false

        override fun isCancelled(): Boolean {
            return cancelled
        }

        override fun processNewLines(lines: Array<out String>?) {
            onProcessNewLine(lines ?: emptyArray())
            cancelled = true
        }

    }
}