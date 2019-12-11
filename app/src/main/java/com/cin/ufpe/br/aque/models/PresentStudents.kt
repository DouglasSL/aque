package com.cin.ufpe.br.aque.models

data class PresentStudents(
    val day: Int,
    val month: Int,
    val year: Int,
    val students: List<String>
)