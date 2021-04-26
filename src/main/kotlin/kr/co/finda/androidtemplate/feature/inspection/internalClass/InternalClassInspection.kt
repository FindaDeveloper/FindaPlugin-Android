package kr.co.finda.androidtemplate.feature.inspection.internalClass

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.isTopLevelKtOrJavaMember

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
            classOrObject.nameIdentifier as PsiElement,
            "Internal 패키지 내부에선 internal 접근 제한자를 사용해야합니다!"
        )
    }
}