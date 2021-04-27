package kr.co.finda.androidtemplate.feature.waistUp

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import kotlinx.coroutines.*

object WaistUpService {

    private val balloonGroup = NotificationGroup(
        "FindaTest",
        NotificationDisplayType.BALLOON,
        true
    )

    private val state: WaistUpStateComponent by lazy {
        WaistUpStateComponent.createInstance()
    }

    var job: Job? = null

    init {
        setNotificationEnable(state.isEnabled)
    }

    fun setNotificationEnable(isEnable: Boolean) {
        job?.cancel()
        if (isEnable) startNotification()
    }

    private fun startNotification() {
        job = GlobalScope.launch {
            while (true) {
                val notification = balloonGroup.createNotification(
                    title = "허리를 FINDA!",
                    content = "Finda 플러그인은 당신의 건강을 책임집니다!",
                    type = NotificationType.WARNING
                )
                notification.addAction(SetWaistUpStateAction())
                notification.notify(null)

                delay(state.dialogDisplayTime)
                notification.expire()

                delay(state.dialogWaitTime)
            }
        }
    }
}