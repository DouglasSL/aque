package com.cin.ufpe.br.aque.dtos

import java.time.DayOfWeek
import java.time.LocalDateTime

class ProfessorClassInfoDto(val className: String,
                          val studentNumber: Int,
                          val firstDayOfWeek: DayOfWeek,
                          val secondDayOfWeek: DayOfWeek,
                          val firstStartTime: LocalDateTime,
                          val secondStartTime: LocalDateTime,
                          val firstEndTime: LocalDateTime,
                          val secondEndTime: LocalDateTime) {
}