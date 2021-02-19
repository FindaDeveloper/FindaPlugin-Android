package kr.co.finda.androidtemplate.feature.waistUp

import com.intellij.notification.*
import com.intellij.openapi.project.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WaistUpService(
    private val project: Project
) {

    private val balloonGroup = NotificationGroup(
        "FindaTest",
        NotificationDisplayType.BALLOON,
        true
    )

    init {
        startNotification()
    }

    private fun startNotification() {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                val notification = balloonGroup.createNotification(
                    title = "허리펴!",
                    content = "Finda 플러그인은 당신의 건강을 책임집니다",
                    type = NotificationType.WARNING
                )
                notification.notify(project)

                delay(3000)
                notification.expire()

                delay(8000)
            }
        }
    }
}