package com.cin.ufpe.br.aque.models

data class PresentStudents(
    val day: Int? = null,
    val month: Int? = null,
    val year: Int? = null,
    val students: List<String>? = null
)