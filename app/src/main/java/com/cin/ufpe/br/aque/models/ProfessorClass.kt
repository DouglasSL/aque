package com.cin.ufpe.br.aque.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "professor_class",
        primaryKeys = ["professorCpf", "classId"],
        foreignKeys = [ForeignKey(
            entity = Professor::class,
            parentColumns = ["cpf"],
            childColumns = ["professorCpf"],
            onDelete = CASCADE
        ), ForeignKey(
                entity = Class::class,
                parentColumns = ["id"],
                childColumns = ["classId"],
                onDelete = CASCADE
        )]
)
class ProfessorClass(
    var professorCpf: String,
    var classId: String
) {

}