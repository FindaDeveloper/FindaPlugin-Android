package kr.co.finda.androidtemplate.feature.addServerConfig.dialog

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementFactory
import kr.co.finda.androidtemplate.type.ServerConfig

class AddServerConfigPresenter(
    private val view: AddServerConfigContract.View,
) : AddServerConfigContract.Presenter {

    override fun onAddServerConfig(
        project: Project,
        propertyName: String,
        devValue: String,
        stgValue: String,
        uatValue: String,
        prdValue: String,
        serverConfig: ServerConfig
    ) {
        WriteCommandAction.runWriteCommandAction(project) {
            serverConfig.dev.add(createFieldElement(project, propertyName, devValue))
            serverConfig.stg.add(createFieldElement(project, propertyName, stgValue))
            serverConfig.uat.add(createFieldElement(project, propertyName, uatValue))
            serverConfig.prd.add(createFieldElement(project, propertyName, prdValue))
        }
    }

    private fun createFieldElement(project: Project, propertyName: String, value: String): PsiElement {
        val fieldText = "public static String $propertyName = \"${value}\""
        return PsiElementFactory.getInstance(project)
            .createFieldFromText(fieldText, null)
    }
}