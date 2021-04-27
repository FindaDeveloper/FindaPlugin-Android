package kr.co.finda.androidtemplate.type

import com.intellij.psi.PsiClass

data class ServerConfig(
    val dev: PsiClass,
    val stg: PsiClass,
    val uat: PsiClass,
    val prd: PsiClass,
)