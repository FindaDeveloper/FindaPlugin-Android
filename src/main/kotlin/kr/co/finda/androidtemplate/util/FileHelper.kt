package kr.co.finda.androidtemplate.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiJavaFile
import com.intellij.util.ResourceUtil
import kr.co.finda.androidtemplate.ext.replaceAll
import kr.co.finda.androidtemplate.type.FileExtension
import kr.co.finda.androidtemplate.type.ServerConfig
import org.jetbrains.kotlin.idea.core.util.toPsiFile

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

    fun isModuleDirectory(directory: VirtualFile): Boolean

    fun getServerConfig(project: Project, directory: VirtualFile): ServerConfig
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

    override fun isModuleDirectory(directory: VirtualFile): Boolean {
        return directory.children.toList().any {
            it.name.contains("build.gradle")
        }
    }

    override fun getServerConfig(project: Project, directory: VirtualFile): ServerConfig {
        return ServerConfig(
            dev = getServerConfigFileByFlavor(project, "dev", directory)!!,
            stg = getServerConfigFileByFlavor(project, "stg", directory)!!,
            uat = getServerConfigFileByFlavor(project, "uat", directory)!!,
            prd = getServerConfigFileByFlavor(project, "prd", directory)!!,
        )
    }

    private fun getServerConfigFileByFlavor(project: Project, flavor: String, moduleDirectory: VirtualFile): PsiClass? {
        val psiFile =
            moduleDirectory.findOrCreateFileByRelativePath("src/${flavor}/java/kr/co/finda/finda/config/ServerConfig.java")
                .toPsiFile(project) as? PsiJavaFile

        return psiFile?.children?.first { it.text.contains("ServerConfig") && it is PsiClass } as? PsiClass
    }

    private fun VirtualFile.findOrCreateFileByRelativePath(relativePath: String): VirtualFile {
        return relativePath.split("/").fold(this) { virtualFile, s ->
            val child = virtualFile.findOrCreateChildData(this, s)
            child
        }
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