package io.classanalyzer.service

import io.classanalyzer.dto.TypeSimpleInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import spoon.Launcher
import spoon.reflect.CtModel
import java.io.File
import java.io.FileWriter


class TypeSimpleInfoProviderTest {

    private val typeSimpleInfoProvider = TypeSimpleInfoProvider()
    private val ctModel: CtModel
    private val typeSimpleInfoList: List<TypeSimpleInfo>

    init {
        val launcher = Launcher()

        // (cf.) use()는 let()과 비슷한데, Closeable에 대해 호출하는 함수
        File.createTempFile(SIMPLE_NAME_OF_CLASS_WITH_GENERIC_TYPE, ".java")
            .also { FileWriter(it).use { writer -> writer.write(classTextWithGenericType) } }
            .also { launcher.addInputResource(it.absolutePath) }
        File.createTempFile(SIMPLE_NAME_OF_CLASS_WITH_MANY_METHODS, ".java")
            .also { FileWriter(it).use { writer -> writer.write(classTextWithManyMethods) } }
            .also { launcher.addInputResource(it.absolutePath) }
        File.createTempFile(SIMPLE_NAME_OF_CLASS_WITH_MEMBER_DECLARED_WITHOUT_ACCESS_MODIFIER, ".java")
            .also { FileWriter(it).use { writer -> writer.write(classTextWithMemberDeclaredWithoutAccessModifier) } }
            .also { launcher.addInputResource(it.absolutePath) }
        File.createTempFile(SIMPLE_NAME_OF_INTERFACE_WITH_MEMBER_DECLARED_WITHOUT_ACCESS_MODIFIER, ".java")
            .also { FileWriter(it).use { writer -> writer.write(interfaceTextWithMemberDeclaredWithoutAccessModifier) } }
            .also { launcher.addInputResource(it.absolutePath) }

        ctModel = launcher.buildModel()

        typeSimpleInfoList = typeSimpleInfoProvider.provideTypeSimpleInfoList(ctModel)
    }

    @Test
    fun givenClassWithGenericTypeWhenTypeSimpleInfoProviderSimplifiedTargetClassThenSimpleInfoOfTypeWithGenericIsEqualToRepresentationTextOfType() {
        val simpleInfoOfClassWithGenericType = typeSimpleInfoList.first { it.simpleName == SIMPLE_NAME_OF_CLASS_WITH_GENERIC_TYPE }

        assertThat(simpleInfoOfClassWithGenericType.simpleName).isEqualTo(SIMPLE_NAME_OF_CLASS_WITH_GENERIC_TYPE)
        assertThat(simpleInfoOfClassWithGenericType.fields[0].type).isEqualTo(TYPE_TEXT_OF_FIRST_FIELD)
        assertThat(simpleInfoOfClassWithGenericType.methods[0].parameterTypeList).isEqualTo(
            listOf(TYPE_TEXT_OF_FIRST_PARAMETER_IN_FIRST_METHOD, TYPE_TEXT_OF_SECOND_PARAMETER_IN_FIRST_METHOD)
        )
        assertThat(simpleInfoOfClassWithGenericType.methods[0].returnType).isEqualTo(RETURN_TYPE_TEXT_OF_FIRST_METHOD)
    }

    @Test
    fun givenClassWithManyMethodsWhenTypeSimpleInfoProviderSimplifiedTargetClassThenDeclaredOrderInTargetClassIsPreserved() {
        val simpleInfoOfClassWithManyMethods = typeSimpleInfoList.first { it.simpleName == SIMPLE_NAME_OF_CLASS_WITH_MANY_METHODS }

        assertThat(simpleInfoOfClassWithManyMethods.simpleName).isEqualTo(SIMPLE_NAME_OF_CLASS_WITH_MANY_METHODS)
        assertThat(simpleInfoOfClassWithManyMethods.methods[0].simpleName).isEqualTo(NAME_OF_FIRST_METHOD)
        assertThat(simpleInfoOfClassWithManyMethods.methods[1].simpleName).isEqualTo(NAME_OF_SECOND_METHOD)
        assertThat(simpleInfoOfClassWithManyMethods.methods[2].simpleName).isEqualTo(NAME_OF_THIRD_METHOD)
        assertThat(simpleInfoOfClassWithManyMethods.methods[3].simpleName).isEqualTo(NAME_OF_FOURTH_METHOD)
        assertThat(simpleInfoOfClassWithManyMethods.methods[4].simpleName).isEqualTo(NAME_OF_FIFTH_METHOD)
    }

    @Test
    fun givenClassWithMemberDeclaredWithoutAccessModifierWhenTypeSimpleInfoProviderSimplifiedTargetClassThenVisibilitySimpleInfoOfMemberDeclaredWithoutAccessModifierIsPackage() {
        /*
        * access modifier가 명시되지 않은 경우, 단순하게 visibility에 toString()을 호출하면 NPE 발생
        * - Cannot invoke "spoon.reflect.declaration.ModifierKind.toString()" because the return value of "spoon.reflect.declaration.CtMethod.getVisibility()" is null
        * */
        val simpleInfoOfClassWithMethodWithoutModifier = typeSimpleInfoList.first { it.simpleName == SIMPLE_NAME_OF_CLASS_WITH_MEMBER_DECLARED_WITHOUT_ACCESS_MODIFIER }

        assertThat(simpleInfoOfClassWithMethodWithoutModifier.simpleName).isEqualTo(SIMPLE_NAME_OF_CLASS_WITH_MEMBER_DECLARED_WITHOUT_ACCESS_MODIFIER)
        assertThat(simpleInfoOfClassWithMethodWithoutModifier.methods[0].visibility).isEqualTo("package")
    }

    @Test
    fun givenInterfaceWithMemberDeclaredWithoutAccessModifierWhenTypeSimpleInfoProviderSimplifiedTargetInterfaceThenVisibilitySimpleInfoOfMemberDeclaredWithoutAccessModifierIsPackage() {
        val simpleInfoOfInterfaceWithMethodWithoutModifier = typeSimpleInfoList.first { it.simpleName == SIMPLE_NAME_OF_INTERFACE_WITH_MEMBER_DECLARED_WITHOUT_ACCESS_MODIFIER }

        assertThat(simpleInfoOfInterfaceWithMethodWithoutModifier.simpleName).isEqualTo(SIMPLE_NAME_OF_INTERFACE_WITH_MEMBER_DECLARED_WITHOUT_ACCESS_MODIFIER)
        assertThat(simpleInfoOfInterfaceWithMethodWithoutModifier.methods[0].visibility).isEqualTo("public")
    }

    companion object {
        private const val SIMPLE_NAME_OF_CLASS_WITH_GENERIC_TYPE = "ClassWithGenericType"
        private const val TYPE_TEXT_OF_FIRST_FIELD = "List<String>"
        private const val RETURN_TYPE_TEXT_OF_FIRST_METHOD = "Set<Map<String, Map<Long, List<String>>>>"
        private const val TYPE_TEXT_OF_FIRST_PARAMETER_IN_FIRST_METHOD = "String[]"
        private const val TYPE_TEXT_OF_SECOND_PARAMETER_IN_FIRST_METHOD = "Map<Long, Object>"

        private val classTextWithGenericType = """
            public class $SIMPLE_NAME_OF_CLASS_WITH_GENERIC_TYPE {
                public $TYPE_TEXT_OF_FIRST_FIELD fieldTest = null;
                public $RETURN_TYPE_TEXT_OF_FIRST_METHOD methodTest($TYPE_TEXT_OF_FIRST_PARAMETER_IN_FIRST_METHOD arg1, $TYPE_TEXT_OF_SECOND_PARAMETER_IN_FIRST_METHOD arg2) {
                    return null;
                }
            }
        """.trimIndent()

        private const val SIMPLE_NAME_OF_CLASS_WITH_MANY_METHODS = "ClassWithManyMethods"
        private const val NAME_OF_FIRST_METHOD = "firstMethod"
        private const val NAME_OF_SECOND_METHOD = "secondMethod"
        private const val NAME_OF_THIRD_METHOD = "thirdMethod"
        private const val NAME_OF_FOURTH_METHOD = "fourthMethod"
        private const val NAME_OF_FIFTH_METHOD = "fifthMethod"

        private val classTextWithManyMethods = """
            public class $SIMPLE_NAME_OF_CLASS_WITH_MANY_METHODS {
                public void $NAME_OF_FIRST_METHOD() {}
                
                public void $NAME_OF_SECOND_METHOD() {}
                
                public void $NAME_OF_THIRD_METHOD() {}
                
                public void $NAME_OF_FOURTH_METHOD() {}
                
                public void $NAME_OF_FIFTH_METHOD() {}
            }
        """.trimIndent()

        private const val SIMPLE_NAME_OF_CLASS_WITH_MEMBER_DECLARED_WITHOUT_ACCESS_MODIFIER = "ClassWithMemberDeclaredWithoutAccessModifier"

        private val classTextWithMemberDeclaredWithoutAccessModifier = """
            public class $SIMPLE_NAME_OF_CLASS_WITH_MEMBER_DECLARED_WITHOUT_ACCESS_MODIFIER {
                String fieldDeclaredWithoutAccessModifier = "";
            
                void methodDeclaredWithoutAccessModifier() {}
            }
        """.trimIndent()

        private const val SIMPLE_NAME_OF_INTERFACE_WITH_MEMBER_DECLARED_WITHOUT_ACCESS_MODIFIER = "InterfaceWithMemberDeclaredWithoutAccessModifier"

        private val interfaceTextWithMemberDeclaredWithoutAccessModifier = """
            interface $SIMPLE_NAME_OF_INTERFACE_WITH_MEMBER_DECLARED_WITHOUT_ACCESS_MODIFIER {
            
                void methodDeclaredWithoutAccessModifier();
            }
        """.trimIndent()
    }
}
