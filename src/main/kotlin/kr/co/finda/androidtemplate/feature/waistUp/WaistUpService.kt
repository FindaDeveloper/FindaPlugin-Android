package kr.co.finda.androidtemplate.feature.waistUp

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.finda.androidtemplate.feature.waistUp.action.SetWaistUpStateAction
import kr.co.finda.androidtemplate.type.WaistUpState

@State(
    name = "WaistUp",
    storages = [Storage("FindaPlugin.xml")]
)
object WaistUpService : PersistentStateComponent<WaistUpState> {

    private val balloonGroup = NotificationGroup(
        "FindaTest",
        NotificationDisplayType.BALLOON,
        true
    )

    private var state = WaistUpState()

    init {
        startNotification()
    }

    private fun startNotification() {
        GlobalScope.launch {
            while (true) {
                if (!state.isEnabled) {
                    break
                }

                val notification = balloonGroup.createNotification(
                    title = "허리펴!",
                    content = "Finda 플러그인은 당신의 건강을 책임집니다",
                    type = NotificationType.WARNING
                )
                notification.addAction(SetWaistUpStateAction())
                notification.notify(null)

                delay(state.hideDelay)
                notification.expire()

                delay(state.waitDelay)
            }
        }
    }

    override fun getState(): WaistUpState {
        return state
    }

    override fun loadState(state: WaistUpState) {
        this.state = state
    }
}