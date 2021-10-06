package kr.co.finda.findaplugin.util

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ScriptRunnerUtil
import com.intellij.execution.util.ExecUtil
import com.intellij.openapi.project.Project
import java.nio.charset.Charset

object CommandLineUtil {

    fun executeCommand(project: Project, command: String): String {
        val commandLine = GeneralCommandLine(command.split(" "))
        commandLine.charset = Charset.forName("UTF-8")
        commandLine.setWorkDirectory(project.basePath)

        ExecUtil.execAndGetOutput(commandLine)
        return ScriptRunnerUtil.getProcessOutput(commandLine, ScriptRunnerUtil.STDOUT_OUTPUT_KEY_FILTER, 3000)
    }
}