package kr.co.finda.findaplugin.util

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager

interface FileHelper {

    fun getLayoutDirectory(selectedDirectoryPath: String): VirtualFile?
}

class FileHelperImpl : FileHelper {

    override fun getLayoutDirectory(selectedDirectoryPath: String): VirtualFile? {
        val mainPath = selectedDirectoryPath.split("src/main")[0]
        val layoutPath = "${mainPath}/src/main/res/layout"
        return VirtualFileManager.getInstance()
            .findFileByUrl("file://${layoutPath}")
    }
}