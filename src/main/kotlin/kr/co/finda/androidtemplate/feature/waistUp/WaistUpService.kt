package kr.co.finda.androidtemplate.feature.waistUp

import com.intellij.openapi.project.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WaistUpService(
    private val project: Project
) {

    init {
        startNotification()
    }

    private fun startNotification() {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                WaistUpNotification().notify(project)
                delay(10000)
            }
        }
    }
}