package com.cin.ufpe.br.aque.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.IllegalArgumentException
import java.time.DayOfWeek

object DayOfWeekUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getPortugueseDayOfWeek(dayOfWeek: DayOfWeek) : String {
        if(dayOfWeek == DayOfWeek.MONDAY) {
            return "Segunda"
        }
        if(dayOfWeek == DayOfWeek.TUESDAY) {
            return "Terça"
        }
        if(dayOfWeek == DayOfWeek.WEDNESDAY) {
            return "Quarta"
        }
        if(dayOfWeek == DayOfWeek.THURSDAY) {
            return "Quinta"
        }
        if(dayOfWeek == DayOfWeek.FRIDAY) {
            return "Sexta"
        }
        if(dayOfWeek == DayOfWeek.SATURDAY) {
            return "Sábado"
        }
        if(dayOfWeek == DayOfWeek.SUNDAY) {
            return "Domingo"
        }

        throw IllegalArgumentException()
    }
}