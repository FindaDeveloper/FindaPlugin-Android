package kr.co.finda.androidtemplate.util

import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.ResourceUtil
import kr.co.finda.androidtemplate.model.Template
import kr.co.finda.androidtemplate.model.TemplateParam

object TemplateUtil {

    fun createFileWithTemplate(
        template: Template,
        fileNameWithExtension: String,
        targetDirectory: VirtualFile,
        vararg params: Pair<TemplateParam, String>
    ) {
        val createdFile = targetDirectory.createChildData(this, fileNameWithExtension)
        val templateContent = getTemplateContentByName(template.templateFileName)

        val replaced = params.fold(templateContent) { acc, pair ->
            acc.replace(pair.first.replacementString, pair.second)
        }

        VfsUtil.saveText(createdFile, replaced)
    }

    private fun getTemplateContentByName(templateName: String): String {
        val templateFileInputStream = ResourceUtil.getResourceAsStream(
            javaClass.classLoader,
            "templates",
            templateName
        )
        return templateFileInputStream.bufferedReader()
            .readLines()
            .reduce { acc, s -> "${acc}\n${s}" }
    }
}