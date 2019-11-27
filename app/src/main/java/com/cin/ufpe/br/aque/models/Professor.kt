package com.cin.ufpe.br.aque.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "professor")
data class Professor(
    @PrimaryKey var cpf: String,
    var name: String
) {
    override fun toString(): String {
        return this.name
    }
}