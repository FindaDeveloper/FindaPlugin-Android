package kr.co.finda.androidtemplate.feature.inspection.internalClass.quickFix

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiFormatUtil
import org.jetbrains.kotlin.idea.core.appendModifier
import org.jetbrains.kotlin.j2k.accessModifier
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.addRemoveModifier.setModifierList
import org.jetbrains.kotlin.psi.psiUtil.getChildOfType
import org.jetbrains.kotlin.psi.psiUtil.getChildrenOfType
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