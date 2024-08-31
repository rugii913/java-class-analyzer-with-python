package io.classanalyzer

import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spoon.JarLauncher
import java.io.File
import java.io.IOException

class MainController {
    private val logger: Logger = LoggerFactory.getLogger(MainController::class.java)

    fun control() {
        // 기초적인 사용법 https://spoon.gforge.inria.fr/launcher.html
        // API docs https://spoon.gforge.inria.fr/mvnsites/spoon-core/apidocs/
        val targetJarPath = File("target-jar\\java-class-analyzer-with-spoon-1.0-SNAPSHOT.jar")
        val decompiledSourceCodePath = File("target-jar\\out")

        val jarLauncher = JarLauncher(targetJarPath.path, decompiledSourceCodePath.path)
        jarLauncher.buildModel()
        val model = jarLauncher.model

        for (compileTimeType in model.allTypes) {
            println(compileTimeType.simpleName)
            println(compileTimeType.fields)
        }

        try {
            if (decompiledSourceCodePath.exists()) {
                FileUtils.cleanDirectory(decompiledSourceCodePath)
            }
        } catch (e: IOException) {
            logger.error("decompile 파일 삭제 과정 중 오류", e)
            throw RuntimeException(e)
        }
    }
}
