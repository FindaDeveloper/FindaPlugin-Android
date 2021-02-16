package kr.co.finda.androidtemplate.feature.waistUp

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import icons.Icons

class WaistUpNotification : Notification(
    "FINDA",
    "허리펴!",
    "Finda 플러그인은 당신의 건강을 책임집니다",
    NotificationType.INFORMATION
) {

}