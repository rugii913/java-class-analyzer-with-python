package io.classanalyzer.service

import io.classanalyzer.dto.FieldSimpleInfo
import io.classanalyzer.dto.MethodSimpleInfo
import io.classanalyzer.dto.TypeSimpleInfo
import spoon.reflect.CtModel
import spoon.reflect.declaration.CtField
import spoon.reflect.declaration.CtMethod
import spoon.reflect.declaration.CtType

class TypeSimpleInfoProvider {

    fun provideTypeSimpleInfoList(ctModel: CtModel): List<TypeSimpleInfo> {
        return ctModel.allTypes.map { refineToTypeSimpleInfo(it) }
    }

    private fun <T> refineToTypeSimpleInfo(ctType: CtType<T>): TypeSimpleInfo {
        val fields = ctType.fields.map { refineToFieldSimpleInfo(it) }
        val methods = ctType.methods.map { refineToMethodSimpleInfo(it) }

        return TypeSimpleInfo(
            simpleName = ctType.simpleName,
            fields = fields,
            methods = methods,
        )
    }

    private fun <T> refineToFieldSimpleInfo(ctField: CtField<T>): FieldSimpleInfo {
        return FieldSimpleInfo("", "", "")
    }

    private fun <T> refineToMethodSimpleInfo(ctMethod: CtMethod<T>): MethodSimpleInfo {
        return MethodSimpleInfo("", "", listOf(""), "")
    }
}
