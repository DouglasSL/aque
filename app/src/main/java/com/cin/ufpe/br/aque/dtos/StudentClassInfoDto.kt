package com.cin.ufpe.br.aque.dtos

import java.time.DayOfWeek
import java.time.LocalDateTime

class StudentClassInfoDto(val className: String,
                          val professorName: String,
                          val firstDayOfWeek: DayOfWeek,
                          val secondDayOfWeek: DayOfWeek,
                          val firstStartTime: LocalDateTime,
                          val secondStartTime: LocalDateTime,
                          val firstEndTime: LocalDateTime,
                          val secondEndTime: LocalDateTime) {
}