package kr.co.finda.androidtemplate.model

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiJavaFile
import org.jetbrains.kotlin.idea.core.util.toPsiFile

data class ServerConfigFiles(
    val flavorAndFile: Map<FindaFlavor, PsiClass?>
) {
    companion object {

        fun fromConfigModuleDirectory(project: Project, directory: VirtualFile): ServerConfigFiles {
            val flavorAndFile = FindaFlavor.values().map {
                val psiFile =
                    directory.findOrCreateFileByRelativePath("src/${it.path}/java/kr/co/finda/finda/config/ServerConfig.java")
                        .toPsiFile(project) as? PsiJavaFile

                val classElement = psiFile?.children?.first { element ->
                    element.text.contains("ServerConfig") && element is PsiClass
                } as? PsiClass

                it to classElement
            }.toMap()

            return ServerConfigFiles(flavorAndFile)
        }

        private fun VirtualFile.findOrCreateFileByRelativePath(relativePath: String): VirtualFile {
            return relativePath.split("/").fold(this) { virtualFile, s ->
                val child = virtualFile.findOrCreateChildData(this, s)
                child
            }
        }
    }
}