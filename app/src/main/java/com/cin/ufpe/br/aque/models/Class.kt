package com.cin.ufpe.br.aque.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "class")
data class Class(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var classId: String,
    var className: String,
    var day: Int,
    var startHour: Int,
    var endHour: Int
) {
    override fun toString(): String {
        return className
    }
}
