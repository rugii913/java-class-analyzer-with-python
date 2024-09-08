package io.classanalyzer

import io.classanalyzer.dto.TypeSimpleInfo
import io.classanalyzer.service.TypeSimpleInfoProvider
import org.slf4j.LoggerFactory
import spoon.JarLauncher
import java.io.File
import java.io.IOException

class ClassAnalyzerController {

    private val logger = LoggerFactory.getLogger(ClassAnalyzerController::class.java)
    private val decompiledCodeDirectory = File("temp\\decompiled")
    private val typeSimpleInfoProvider = TypeSimpleInfoProvider()

    fun analyze(targetJarPath: String): List<TypeSimpleInfo> {
        // 기초적인 사용법 https://spoon.gforge.inria.fr/launcher.html
        // API docs https://spoon.gforge.inria.fr/mvnsites/spoon-core/apidocs/
        val jarLauncher = JarLauncher(targetJarPath, decompiledCodeDirectory.path)
        jarLauncher.buildModel()
        return typeSimpleInfoProvider.provideTypeSimpleInfoList(jarLauncher.model)
    }

    fun cleanDecompiled() {
        try {
            if (decompiledCodeDirectory.exists()) {
                decompiledCodeDirectory.deleteRecursively()
            }
        } catch (e: IOException) {
            logger.error("decompile 파일 삭제 과정 중 오류", e)
            throw RuntimeException(e)
        }
    }
}
