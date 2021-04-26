package kr.co.finda.androidtemplate.feature.inspection.internalClass

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.util.AccessModifier
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFile

class InternalClassInspectionPresenter(
    private val view: InternalClassInspectionContract.View
) : InternalClassInspectionContract.Presenter {

    override fun onVisit(holder: ProblemsHolder, classOrObject: KtClassOrObject) {
        val isInternalFile = (classOrObject.parent as? KtFile)
            ?.packageFqName.toString().contains("internal")
        val isInternalModifier = classOrObject.modifierList?.text == "internal"
        if (classOrObject.isTopLevel() && isInternalFile && !isInternalModifier) {
            view.registerProblem(holder, classOrObject)
        }
    }
}