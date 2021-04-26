package kr.co.finda.androidtemplate.feature.inspection.internalClass.quickFix

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.psi.KtClass

class InternalClassQuickFix : LocalQuickFix {
    override fun getFamilyName(): String {
        return "internal 접근제한자로 변경"
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        println("type:" + (descriptor.psiElement.parent as KtClass).modifier)
    }
}