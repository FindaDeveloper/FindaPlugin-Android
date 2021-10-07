package kr.co.finda.findaplugin.model

enum class TemplateParam(val replacementString: String) {
    NAME("\$NAME$"),
    PACKAGE("\$PACKAGE$"),
    LAYOUT("\$LAYOUT_NAME$"),
    VIEW_MODEL_PACKAGE("\$VM_PACKAGE")
}