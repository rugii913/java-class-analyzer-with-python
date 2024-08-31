package io.classanalyzer

import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spoon.JarLauncher
import spoon.reflect.CtModel
import java.io.File
import java.io.IOException

class ClassAnalyzerController {

    private val logger: Logger = LoggerFactory.getLogger(ClassAnalyzerController::class.java)
    private val decompiledCodeDirectory = File("temp\\decompiled")

    fun analyze(targetJarPath: String): CtModel {
        // 기초적인 사용법 https://spoon.gforge.inria.fr/launcher.html
        // API docs https://spoon.gforge.inria.fr/mvnsites/spoon-core/apidocs/
        val jarLauncher = JarLauncher(targetJarPath, decompiledCodeDirectory.path)
        jarLauncher.buildModel()
        val model = jarLauncher.model

        return model
    }

    fun cleanDecompiled() {
        try {
            if (decompiledCodeDirectory.exists()) {
                FileUtils.cleanDirectory(decompiledCodeDirectory)
            }
        } catch (e: IOException) {
            logger.error("decompile 파일 삭제 과정 중 오류", e)
            throw RuntimeException(e)
        }
    }
}
