package kr.co.finda.androidtemplate.feature.inspection.camelCase

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.KtVisitorVoid
import org.jetbrains.kotlin.psi.namedDeclarationVisitor

class CamelCaseInspection : AbstractKotlinInspection() {

    override fun getDisplayName(): String {
        return "Use CarmelCase format"
    }

    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean
    ): KtVisitorVoid {
        return namedDeclarationVisitor { declaredName ->
            if (declaredName.name?.isDefinedCamelCase() == false) {
                holder.registerProblem(
                    declaredName.nameIdentifier as PsiElement,
                    "Please use CamelCase for #ref #loc"
                )
            }
        }
    }

    private fun String.isDefinedCamelCase(): Boolean {
        val charArray = toCharArray()
        return charArray.mapIndexed { index, char ->
            char to charArray.getOrNull(index + 1)
        }.none {
            it.first.isUpperCase() &&
                    it.second?.isUpperCase() == true
        }
    }

    override fun isEnabledByDefault(): Boolean {
        return true
    }
}