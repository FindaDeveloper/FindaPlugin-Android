package kr.co.finda.androidtemplate.persistent

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "WaistUp",
    storages = [Storage(value = "WaistUp.xml")]
)
class WaistUpStateComponent : PersistentStateComponent<WaistUpStateComponent> {

    @JvmField
    var isEnabled: Boolean = true

    @JvmField
    var dialogDisplayTime: Long = 2000

    @JvmField
    var dialogWaitTime: Long = 10000

    override fun getState(): WaistUpStateComponent {
        return this
    }

    override fun loadState(state: WaistUpStateComponent) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun createInstance(): WaistUpStateComponent =
            ServiceManager.getService(WaistUpStateComponent::class.java)
    }
}