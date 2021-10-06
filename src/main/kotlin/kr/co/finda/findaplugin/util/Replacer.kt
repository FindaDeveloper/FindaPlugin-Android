package kr.co.finda.findaplugin.util

class ReplacerImpl : Replacer {

    override fun replace(
        content: String,
        replacements: Replacements
    ): String {
        return content.replaceAllIfNotNull("\$PACKAGE$", replacements.packageName)
            .replaceAllIfNotNull("\$LAYOUT_NAME$", replacements.layoutName)
            .replaceAllIfNotNull("\$NAME$", replacements.name)
            .replaceAllIfNotNull("\$VM_PACKAGE$", replacements.viewModelPackage)
    }
}