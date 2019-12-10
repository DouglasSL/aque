package com.cin.ufpe.br.aque.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "professor")
data class Professor(
    @PrimaryKey var email: String? = null,
    var name: String? = null,
    var universityName: String? = null,
    var cpf: String? = null,
    var password: String? = null
) {
    override fun toString(): String {
        if(this.name == null)
            return ""
        return this.name!!
    }
}