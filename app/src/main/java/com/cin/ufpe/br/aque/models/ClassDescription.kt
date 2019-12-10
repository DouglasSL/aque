package com.cin.ufpe.br.aque.models

data class ClassDescription(
    val id: String? = null,
    val className: String? = null,
    val firstDay: Int? = null,
    val secondDay: Int? = null,
    val firstDayStartHour: Int? = null,
    val firstDayEndHour: Int? = null,
    val secondDayStartHour: Int? = null,
    val secondDayEndHour: Int? = null
)