package kr.co.finda.androidtemplate.action.resign

import com.intellij.openapi.project.Project
import kr.co.finda.androidtemplate.type.AabType

interface ResignContract {
    interface View {
        fun showErrorDialog(message: String)
        fun onSuccess()
    }

    interface Presenter {
        fun onResign(project: Project, aabType: AabType, password: String)
    }
}