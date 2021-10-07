package kr.co.finda.findaplugin.util

object DirectoryUtil {

    fun getPackageByPath(path: String): String {
        val splited = path.split("(java/|kotlin/)".toRegex())
        if (splited.size <= 1) {
            return ""
        }
        return splited[1].replace("/", ".")
    }
}