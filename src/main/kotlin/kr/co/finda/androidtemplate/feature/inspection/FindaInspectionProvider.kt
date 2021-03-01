package kr.co.finda.androidtemplate.feature.inspection

import com.intellij.codeInspection.InspectionToolProvider
import com.intellij.codeInspection.LocalInspectionTool
import kr.co.finda.androidtemplate.feature.inspection.camelCase.CamelCaseInspection

class FindaInspectionProvider : InspectionToolProvider {
    override fun getInspectionClasses(): Array<Class<out LocalInspectionTool>> {
        return arrayOf(CamelCaseInspection::class.java)
    }
}