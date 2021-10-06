package kr.co.finda.findaplugin.action.inspection.internalClass.quickFix

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

class InternalClassQuickFix : LocalQuickFix {
    override fun getFamilyName(): String {
        return "internal 접근제한자로 변경"
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val classElement = descriptor.psiElement.parent as KtClassOrObject
        val visibilityModifier = classElement.visibilityModifier() as? LeafPsiElement
        if (visibilityModifier != null) {
            visibilityModifier.replaceWithText("internal")
            return
        }

        classElement.addModifier(KtTokens.INTERNAL_KEYWORD)
    }
}