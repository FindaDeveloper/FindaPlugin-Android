package kr.co.finda.androidtemplate.common

import kr.co.finda.androidtemplate.ext.replaceAll
import kr.co.finda.androidtemplate.ext.replaceAllIfNotNull

interface Replacer {

    fun replace(content: String, replacements: Replacements): String
}

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

data class Replacements(
    val packageName: String? = null,
    val layoutName: String? = null,
    val name: String? = null,
    val viewModelPackage: String? = null
)