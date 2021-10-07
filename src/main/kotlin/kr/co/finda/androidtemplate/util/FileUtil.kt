package kr.co.finda.androidtemplate.util

import com.intellij.openapi.vfs.VirtualFileManager

object FileUtil {

    fun getPackageByPath(path: String): String {
        val splited = path.split("(java/|kotlin/)".toRegex())
        if (splited.size <= 1) {
            return ""
        }
        return splited[1].replace("/", ".")
    }

    fun hasConflictFileName(
        fileNameWithExtension: String,
        basePath: String
    ): Boolean {
        val file = VirtualFileManager.getInstance()
            .findFileByUrl("file://${basePath}/${fileNameWithExtension}")
        return file != null
    }
}