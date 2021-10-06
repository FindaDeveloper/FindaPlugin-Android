package kr.co.finda.findaplugin.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import kr.co.finda.findaplugin.ext.showDialog
import kr.co.finda.findaplugin.util.DeviceUtil

class ShowOverdrawAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        val devices = DeviceUtil.getDebugDevices(project)

        if (devices.isNullOrEmpty()) {
            project.showDialog("연결된 디바이스가 없습니다", "디바이스 연결 상태를 확인해주세요")
            return
        }

        DeviceUtil.toggleShowOverdrawSetting(devices)
    }
}