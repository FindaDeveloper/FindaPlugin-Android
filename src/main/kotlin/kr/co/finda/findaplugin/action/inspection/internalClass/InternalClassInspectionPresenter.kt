package kr.co.finda.findaplugin.action.inspection.internalClass

import com.intellij.codeInspection.ProblemsHolder
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class InternalClassInspectionPresenter(
    private val view: InternalClassInspectionContract.View
) : InternalClassInspectionContract.Presenter {

    override fun onVisit(holder: ProblemsHolder, classOrObject: KtClassOrObject) {
        val isInternalFile = (classOrObject.parent as? KtFile)
            ?.packageFqName.toString().contains("internal")
        val visibilityModifier = classOrObject.modifierList?.visibilityModifier()?.text
        val isUnavailableModifier = visibilityModifier != "internal" && visibilityModifier != "private"

        if (classOrObject.isTopLevel() && isInternalFile && isUnavailableModifier) {
            view.registerProblem(holder, classOrObject)
        }
    }
}