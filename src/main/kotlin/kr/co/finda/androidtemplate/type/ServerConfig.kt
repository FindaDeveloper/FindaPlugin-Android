package kr.co.finda.androidtemplate.type

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile

data class ServerConfig(
    val dev: PsiClass,
    val prd: PsiClass,
    val stg: PsiClass,
    val uat: PsiClass
)