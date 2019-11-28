package com.cin.ufpe.br.aque.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "class")
data class Class(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var className: String,
    var firstDayOfWeek: Int,
    var secondDayOfWeek: Int,
    var firstDayStartHour: Int,
    var firstDayEndHour: Int,
    var secondDayStartHour: Int,
    var secondDayEndHour: Int,
    var firstDayStartMinute: Int,
    var firstDayEndMinute: Int,
    var secondDayStartMinute: Int,
    var secondDayEndMinute: Int
) {
    override fun toString(): String {
        return className
    }
}