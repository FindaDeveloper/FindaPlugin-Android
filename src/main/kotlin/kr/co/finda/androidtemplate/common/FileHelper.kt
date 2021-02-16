package kr.co.finda.androidtemplate.common

import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.util.ResourceUtil
import kr.co.finda.androidtemplate.ext.replaceAll
import kr.co.finda.androidtemplate.model.FileExtension

interface FileHelper {

    fun isFileExistWithPath(path: String): Boolean

    fun createFileWithTemplate(
        directory: VirtualFile,
        name: String,
        fileExtension: FileExtension,
        templateFileName: String,
        replacements: Map<String, String>
    )

    fun getPackageNameByPath(path: String): String

    fun getLayoutDirectory(projectBasePath: String): VirtualFile?
}

class FileHelperImpl : FileHelper {

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
        replacements: Map<String, String>
    ) {
        val kotlinFile = directory.createChildData(this, "${name}.${fileExtension.extension}")

        val templateContent = getTemplateContentByName(templateFileName)

        val replaced = templateContent.replaceAll("@PACKAGE@", replacements["package"])
            .replaceAll("@LAYOUT_NAME@", replacements["layoutname"])
            .replaceAll("@NAME@", replacements["name"])
            .replaceAll("@VM_PACKAGE@", replacements["vmpackage"])

        VfsUtil.saveText(kotlinFile, replaced)
    }

    override fun getPackageNameByPath(path: String): String {
        return try {
            // TODO 정규식으로 변경 필요 'kotlin/' 대응
            path.split("java/")[1]
                .replaceAll("/", ".")
        } catch (e: IndexOutOfBoundsException) {
            ""
        }
    }

    override fun getLayoutDirectory(projectBasePath: String): VirtualFile? {
        return VirtualFileManager.getInstance()
            .findFileByUrl("file://${projectBasePath}/src/main/res/layout")
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