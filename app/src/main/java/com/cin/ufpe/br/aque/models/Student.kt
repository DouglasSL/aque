package com.cin.ufpe.br.aque.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    @PrimaryKey var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var cpf: String? = null,
    var period: Int? = null,
    var graduation: String? = null,
    var password: String? = null
) {
    override fun toString(): String {
        if(this.name != null)
            return this.name!!
        return ""
    }
}