package com.cin.ufpe.br.aque.dtos

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

class StudentClassInfoDto(val className: String,
                          val professorName: String,
                          val firstDayOfWeek: DayOfWeek,
                          val secondDayOfWeek: DayOfWeek,
                          val firstStartTime: Int,
                          val secondStartTime: Int,
                          val firstEndTime: Int,
                          val secondEndTime: Int) {
}