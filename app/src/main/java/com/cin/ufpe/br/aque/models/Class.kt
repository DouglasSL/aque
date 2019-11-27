package com.cin.ufpe.br.aque.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "class")
data class Class(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var className: String,
    var firstDayOfWeek: Int,
    var secondDayOfWeek: Int
) {

}