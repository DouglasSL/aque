package com.cin.ufpe.br.aque.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    @PrimaryKey var cpf: String,
    var name: String,
    var period: Int,
    var graduation: String
) {
    override fun toString(): String {
        return this.name
    }
}