package io.classanalyzer

import org.junit.jupiter.api.Test

class MainControllerTest {

    private val controller: ClassAnalyzerController = ClassAnalyzerController()

    @Test
    fun analyze() {
        controller.analyze("target-jar\\java-class-analyzer-with-spoon-1.0-SNAPSHOT.jar")
    }

    @Test
    fun cleanDecompiled() {
        controller.cleanDecompiled()
    }
}
