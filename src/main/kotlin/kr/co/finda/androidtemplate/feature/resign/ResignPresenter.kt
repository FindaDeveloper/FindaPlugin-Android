package kr.co.finda.androidtemplate.feature.resign

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ScriptRunnerUtil
import com.intellij.execution.util.ExecUtil
import com.intellij.openapi.project.Project
import kr.co.finda.androidtemplate.type.AabType
import java.nio.charset.Charset

class ResignPresenter(
    private val view: ResignContract.View,
) : ResignContract.Presenter {

    override fun onResign(project: Project, aabType: AabType, password: String) {
        val command = if (aabType == AabType.Prd) PRD_RESIGN_COMMAND.format(password)
        else STAGE_RESIGN_COMMAND.format(password)
        val commandLine = GeneralCommandLine(command.split(" "))
        commandLine.charset = Charset.forName("UTF-8")
        commandLine.setWorkDirectory(project.basePath)

        ExecUtil.execAndGetOutput(commandLine)
        val output = ScriptRunnerUtil.getProcessOutput(commandLine, ScriptRunnerUtil.STDOUT_OUTPUT_KEY_FILTER, 3000)

        if (output.contains("error")) {
            view.showErrorDialog(output)
        } else {
            view.onSuccess()
        }
    }

    companion object {
        private const val STAGE_RESIGN_COMMAND = "jarsigner -verbose -sigalg SHA256withRSA " +
                "-digestalg SHA-256 -keystore app/keystore/finda.jks -storepass %s " +
                "appsolid_unsigned_app-stg-release.aab key0"

        private const val PRD_RESIGN_COMMAND = "jarsigner -verbose -sigalg SHA256withRSA " +
                "-digestalg SHA-256 -keystore app/keystore/finda.jks -storepass %s " +
                "appsolid_unsigned_app-prd-release.aab key0"
    }
}