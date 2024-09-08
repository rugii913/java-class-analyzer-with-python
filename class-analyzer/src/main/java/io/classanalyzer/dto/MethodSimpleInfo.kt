package io.classanalyzer.dto

data class MethodSimpleInfo(
    val simpleName: String,
    val visibility: String,
    val parameterTypeList: List<String>,
    val returnType: String,
)
