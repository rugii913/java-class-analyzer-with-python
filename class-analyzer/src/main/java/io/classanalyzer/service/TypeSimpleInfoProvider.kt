package io.classanalyzer.service

import io.classanalyzer.dto.FieldSimpleInfo
import io.classanalyzer.dto.MethodSimpleInfo
import io.classanalyzer.dto.TypeSimpleInfo
import spoon.reflect.CtModel
import spoon.reflect.declaration.CtField
import spoon.reflect.declaration.CtMethod
import spoon.reflect.declaration.CtType
import spoon.reflect.reference.CtTypeReference

class TypeSimpleInfoProvider {

    fun provideTypeSimpleInfoList(ctModel: CtModel): List<TypeSimpleInfo> {
        return ctModel.allTypes.map { refineToTypeSimpleInfo(it) }
    }

    private fun refineToTypeSimpleInfo(ctType: CtType<*>): TypeSimpleInfo {
        val refinedFields = ctType.fields.map { refineToFieldSimpleInfo(it) }
        val refinedMethods = ctType.methods
            .sortedWith(Comparator.comparing { it.position.line }) // 메서드 선언 순서대로 정렬
            .map { refineToMethodSimpleInfo(it) }

        return TypeSimpleInfo(
            simpleName = ctType.simpleName,
            fields = refinedFields,
            methods = refinedMethods,
        )
    }

    private fun refineToFieldSimpleInfo(ctField: CtField<*>) =
        FieldSimpleInfo(
            simpleName = ctField.simpleName,
            visibility = ctField.visibility.toString(),
            type = flattenCtTypeReference(ctField.type),
        )

    private fun refineToMethodSimpleInfo(ctMethod: CtMethod<*>) =
        MethodSimpleInfo(
            simpleName = ctMethod.simpleName,
            visibility = ctMethod.visibility.toString(),
            parameterTypeList = ctMethod.parameters.map { flattenCtTypeReference(it.type) },
            returnType = flattenCtTypeReference(ctMethod.type),
        )

    companion object GenericTypeFlattener {
        fun <T> flattenCtTypeReference(ctTypeReference: CtTypeReference<T>): String {
            val genericTypeList = ctTypeReference.actualTypeArguments
            // (cf.) generic type의 CtTypeReference에 대해서도 재귀적으로 평탄화시킴
            val flattenedGenericType = genericTypeList.joinToString(", ") { flattenCtTypeReference(it) }

            return if (genericTypeList.isNotEmpty()) {
                "${ctTypeReference.simpleName}<$flattenedGenericType>"
            } else {
                ctTypeReference.simpleName
            }
        }
    }
}
