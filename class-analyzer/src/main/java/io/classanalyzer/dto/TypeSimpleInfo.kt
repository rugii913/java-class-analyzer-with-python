package io.classanalyzer.dto

data class TypeSimpleInfo(
    val simpleName: String,
    val fields: List<FieldSimpleInfo>,
    val methods: List<MethodSimpleInfo>,
)
