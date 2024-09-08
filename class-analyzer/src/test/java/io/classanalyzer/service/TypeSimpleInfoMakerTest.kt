package io.classanalyzer.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import spoon.Launcher
import spoon.reflect.CtModel
import java.io.File
import java.io.FileWriter


class TypeSimpleInfoMakerTest {

    private val typeSimpleInfoProvider = TypeSimpleInfoProvider()
    private val targetClassText = """
        class ClassForTest {
            public List<String> fieldTest = null;
            public Set<Map<String, String>> methodTest(String[] arg1, Map<Long, String> arg2) {
                return null;
            }
        }
    """.trimIndent()
    private val targetClassCtModel: CtModel

    init {
        val tempFile = File.createTempFile("ClassForTest", ".java")
        FileWriter(tempFile).use { writer -> writer.write(targetClassText) } // (cf.) use()는 let()과 비슷한데, Closeable에 대해 호출하는 함수

        targetClassCtModel = Launcher().also { it.addInputResource(tempFile.absolutePath) }.buildModel()
    }

    @Test
    fun testProvideTypeSimpleInfoList() {
        val typeSimpleInformation = typeSimpleInfoProvider.provideTypeSimpleInfoList(targetClassCtModel)[0]

        assertThat(typeSimpleInformation.simpleName).isEqualTo("ClassForTest")
        assertThat(typeSimpleInformation.fields[0].type).isEqualTo("List<String>")
        assertThat(typeSimpleInformation.methods[0].parameterTypeList).isEqualTo(listOf("String[]", "Map<Long, String>"))
        assertThat(typeSimpleInformation.methods[0].returnType).isEqualTo("Set<Map<String, String>>")
    }
}