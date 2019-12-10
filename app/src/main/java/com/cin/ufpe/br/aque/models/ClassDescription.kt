package com.cin.ufpe.br.aque.models

data class ClassDescription(
    val id: String,
    val className: String,
    val firstDay: Int,
    val secondDay: Int,
    val firstDayStartHour: Int,
    val firstDayEndHour: Int,
    val secondDayStartHour: Int,
    val secondDayEndHour: Int
)