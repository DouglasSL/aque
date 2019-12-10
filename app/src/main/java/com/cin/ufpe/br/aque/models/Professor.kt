package com.cin.ufpe.br.aque.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "professor")
data class Professor(
    @PrimaryKey var email: String,
    var name: String,
    var universityName: String,
    var cpf: String,
    var password: String
) {
    override fun toString(): String {
        return this.name
    }
}