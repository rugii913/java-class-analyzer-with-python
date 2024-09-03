package io.classanalyzer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spoon.JarLauncher
import spoon.reflect.CtModel
import spoon.reflect.declaration.CtType
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
        /*
        for (targetType in model.allTypes) {
            targetType.declaredFields.forEach {
                val fieldDeclaration = it.fieldDeclaration
                println(fieldDeclaration.getSimpleName())
                println(fieldDeclaration.getVisibility())
                println(fieldDeclaration.getType().simpleName)
            }

            targetType.methods.forEach {
                println(it.getSimpleName())
                println(it.getVisibility())
                println(it.getParameters().map { it.getType().getSimpleName() })
                println(it.getType())
            }
        }
*/
        return model
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
    /*
    fun test(test1: String, test2: List<Map<String, String>>) {
        return
    }
    */
}
