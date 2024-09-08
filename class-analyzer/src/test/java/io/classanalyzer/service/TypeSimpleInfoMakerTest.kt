package io.classanalyzer.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import spoon.Launcher
import spoon.reflect.CtModel
import java.io.File
import java.io.FileWriter


class TypeSimpleInfoMakerTest {

    private val typeSimpleInfoProvider = TypeSimpleInfoProvider()
    private val targetClassTextWithGenericType = """
        class ClassWithGenericType {
            public List<String> fieldTest = null;
            public Set<Map<String, String>> methodTest(String[] arg1, Map<Long, String> arg2) {
                return null;
            }
        }
    """.trimIndent()
    private val ctModelWithGenericType: CtModel

    private val targetClassTextWithManyMethods = """
        class ClassWithGenericType {
            public void firstMethod() {
                return null;
            }
            
            public void secondMethod() {
                return null;
            }
            
            public void thirdMethod() {
                return null;
            }
            
            public void fourthMethod() {
                return null;
            }
            
            public void fifthMethod() {
                return null;
            }
        }
    """.trimIndent()
    private val ctModelWithManyMethods: CtModel

    init {
        val tempFile1 = File.createTempFile("ClassWithGenericType", ".java")
        FileWriter(tempFile1).use { writer -> writer.write(targetClassTextWithGenericType) } // (cf.) use()는 let()과 비슷한데, Closeable에 대해 호출하는 함수
        ctModelWithGenericType = Launcher().also { it.addInputResource(tempFile1.absolutePath) }.buildModel()

        val tempFile2 = File.createTempFile("ClassWithManyMethods", ".java")
        FileWriter(tempFile2).use { writer -> writer.write(targetClassTextWithManyMethods) }
        ctModelWithManyMethods = Launcher().also { it.addInputResource(tempFile2.absolutePath) }.buildModel()
    }

    @Test
    fun testProvideTypeSimpleInfoList() {
        val typeSimpleInformation = typeSimpleInfoProvider.provideTypeSimpleInfoList(ctModelWithGenericType)[0]

        assertThat(typeSimpleInformation.simpleName).isEqualTo("ClassWithGenericType")
        assertThat(typeSimpleInformation.fields[0].type).isEqualTo("List<String>")
        assertThat(typeSimpleInformation.methods[0].parameterTypeList).isEqualTo(listOf("String[]", "Map<Long, String>"))
        assertThat(typeSimpleInformation.methods[0].returnType).isEqualTo("Set<Map<String, String>>")
    }

    @Test
    fun testClassWithManyMethods() {
        val typeSimpleInformation = typeSimpleInfoProvider.provideTypeSimpleInfoList(ctModelWithManyMethods)[0]
        assertThat(typeSimpleInformation.methods[0].simpleName).isEqualTo("firstMethod")
        assertThat(typeSimpleInformation.methods[1].simpleName).isEqualTo("secondMethod")
        assertThat(typeSimpleInformation.methods[2].simpleName).isEqualTo("thirdMethod")
        assertThat(typeSimpleInformation.methods[3].simpleName).isEqualTo("fourthMethod")
        assertThat(typeSimpleInformation.methods[4].simpleName).isEqualTo("fifthMethod")
    }
}