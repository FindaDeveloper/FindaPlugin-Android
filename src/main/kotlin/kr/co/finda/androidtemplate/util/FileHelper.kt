package kr.co.finda.androidtemplate.util

import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.util.ResourceUtil
import kr.co.finda.androidtemplate.ext.replaceAll
import kr.co.finda.androidtemplate.type.FileExtension

interface FileHelper {

    fun isFileExistWithPath(path: String): Boolean

    fun createFileWithTemplate(
        directory: VirtualFile,
        name: String,
        fileExtension: FileExtension,
        templateFileName: String,
        replacements: Replacements
    )

    fun getPackageNameByPath(path: String): String

    fun getLayoutDirectory(selectedDirectoryPath: String): VirtualFile?
}

class FileHelperImpl(
    private val replacer: Replacer
) : FileHelper {

    override fun isFileExistWithPath(path: String): Boolean {
        val file = VirtualFileManager.getInstance()
            .findFileByUrl("file://${path}")
        return file != null
    }

    override fun createFileWithTemplate(
        directory: VirtualFile,
        name: String,
        fileExtension: FileExtension,
        templateFileName: String,
        replacements: Replacements
    ) {
        val kotlinFile = directory.createChildData(this, "${name}.${fileExtension.extension}")

        val templateContent = getTemplateContentByName(templateFileName)

        val replaced = replacer.replace(templateContent, replacements)

        VfsUtil.saveText(kotlinFile, replaced)
    }

    override fun getPackageNameByPath(path: String): String {
        return try {
            val splited =
                if (path.contains("java/")) path.split("java/")
                else path.split("kotlin/")
            return splited[1].replaceAll("/", ".")
        } catch (e: IndexOutOfBoundsException) {
            ""
        }
    }

    override fun getLayoutDirectory(selectedDirectoryPath: String): VirtualFile? {
        val mainPath = selectedDirectoryPath.split("src/main")[0]
        val layoutPath = "${mainPath}/src/main/res/layout"
        return VirtualFileManager.getInstance()
            .findFileByUrl("file://${layoutPath}")
    }

    private fun getTemplateContentByName(templateName: String): String {
        val templateFileInputStream = ResourceUtil.getResourceAsStream(
            javaClass.classLoader,
            "templates",
            "${templateName}.txt"
        )
        return templateFileInputStream.bufferedReader()
            .readLines()
            .reduce { acc, s -> "${acc}\n${s}" }
    }
}