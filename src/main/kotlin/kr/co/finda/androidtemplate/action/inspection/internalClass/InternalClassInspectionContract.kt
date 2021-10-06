package kr.co.finda.androidtemplate.action.inspection.internalClass

import com.intellij.codeInspection.ProblemsHolder
import org.jetbrains.kotlin.psi.KtClassOrObject

interface InternalClassInspectionContract {

    interface View {
        fun registerProblem(holder: ProblemsHolder, classOrObject: KtClassOrObject)
    }

    interface Presenter {
        fun onVisit(holder: ProblemsHolder, classOrObject: KtClassOrObject)
    }
}