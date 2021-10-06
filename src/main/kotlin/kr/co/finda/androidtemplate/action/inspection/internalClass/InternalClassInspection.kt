package kr.co.finda.androidtemplate.action.inspection.internalClass

import com.intellij.codeInspection.ProblemsHolder
import kr.co.finda.androidtemplate.action.inspection.internalClass.quickFix.InternalClassQuickFix
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.*

class InternalClassInspection : AbstractKotlinInspection(), InternalClassInspectionContract.View {

    private val presenter: InternalClassInspectionContract.Presenter by lazy {
        InternalClassInspectionPresenter(this)
    }

    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean
    ): KtVisitorVoid {
        return classOrObjectVisitor { classOrObject ->
            presenter.onVisit(holder, classOrObject)
        }
    }

    override fun isEnabledByDefault(): Boolean {
        return true
    }

    override fun registerProblem(holder: ProblemsHolder, classOrObject: KtClassOrObject) {
        holder.registerProblem(
            classOrObject.nameIdentifier!!,
            "Internal 패키지 내부에선 internal 접근 제한자를 사용해야합니다!",
            InternalClassQuickFix()
        )
    }
}