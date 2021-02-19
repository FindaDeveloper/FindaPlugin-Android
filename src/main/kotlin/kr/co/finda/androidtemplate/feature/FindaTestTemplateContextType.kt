package kr.co.finda.androidtemplate.feature

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile

class FindaTestTemplateContextType : TemplateContextType("FINDA_TEST", "FindaTest") {

    override fun isInContext(file: PsiFile, offset: Int): Boolean {
        return file.name.endsWith("Test.kt")
    }
}