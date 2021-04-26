package kr.co.finda.androidtemplate.feature.inspection

import com.intellij.codeInspection.InspectionToolProvider
import com.intellij.codeInspection.LocalInspectionTool
import kr.co.finda.androidtemplate.feature.inspection.internalClass.InternalClassInspection

class FindaInspectionProvider : InspectionToolProvider {
    override fun getInspectionClasses(): Array<Class<out LocalInspectionTool>> {
        return arrayOf(InternalClassInspection::class.java)
    }
}