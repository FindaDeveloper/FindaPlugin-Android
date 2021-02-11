package kr.co.finda.androidtemplate.models

import com.intellij.openapi.util.ClassLoaderUtil
import com.intellij.util.PathUtil
import com.intellij.util.ResourceUtil
import org.apache.commons.io.IOUtils
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
import org.bouncycastle.crypto.tls.ConnectionEnd.server
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class TemplateInfo(
    screenType: ScreenType
) {

    val codeTemplateContent: String
    val layoutTemplateContent: String
    val viewModelTemplateContent: String

    init {
        codeTemplateContent = getFileByName(screenType.codeFileTemplate)
        layoutTemplateContent = getFileByName("LayoutTemplate.txt")
        viewModelTemplateContent = getFileByName("ViewModelTemplate.txt")
    }

    private fun getFileByName(name: String): String {
        val fileInputStream = ResourceUtil.getResourceAsStream(javaClass.classLoader, "templates", name)
        return fileInputStream.bufferedReader().readLines().reduce { acc, s -> "${acc}\n${s}" }
    }
}