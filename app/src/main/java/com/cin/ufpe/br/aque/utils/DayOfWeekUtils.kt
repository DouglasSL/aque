package com.cin.ufpe.br.aque.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.IllegalArgumentException
import java.time.DayOfWeek

object DayOfWeekUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getPortugueseDayOfWeek(dayOfWeek: Int) : String {
        if(dayOfWeek == 2) {
            return "Segunda"
        }
        if(dayOfWeek == 3) {
            return "Terça"
        }
        if(dayOfWeek == 4) {
            return "Quarta"
        }
        if(dayOfWeek == 5) {
            return "Quinta"
        }
        if(dayOfWeek == 6) {
            return "Sexta"
        }
        if(dayOfWeek == 7) {
            return "Sábado"
        }
        if(dayOfWeek == 1) {
            return "Domingo"
        }

        throw IllegalArgumentException()
    }
}