package kr.co.finda.findaplugin.inspection.internalClass

import com.intellij.codeInspection.ProblemsHolder
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class InternalClassInspection : AbstractKotlinInspection() {

    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean
    ): KtVisitorVoid {
        return classOrObjectVisitor { classOrObject ->
            val isInternalFile = (classOrObject.parent as? KtFile)?.packageFqName.toString().contains("internal")
            if (!isInternalFile && !classOrObject.isTopLevel()) {
                return@classOrObjectVisitor
            }

            val visibilityModifier = classOrObject.modifierList?.visibilityModifier()?.text
            val isUnavailableModifier = visibilityModifier != "internal" && visibilityModifier != "private"

            if (isUnavailableModifier) {
                registerProblem(holder, classOrObject)
            }
        }
    }

    override fun isEnabledByDefault(): Boolean {
        return true
    }

    private fun registerProblem(holder: ProblemsHolder, classOrObject: KtClassOrObject) {
        holder.registerProblem(
            classOrObject.nameIdentifier!!,
            "Internal 패키지 내부에선 internal 접근 제한자를 사용해야합니다!",
            InternalClassQuickFix()
        )
    }
}