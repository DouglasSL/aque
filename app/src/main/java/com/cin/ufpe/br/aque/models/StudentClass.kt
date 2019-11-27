package com.cin.ufpe.br.aque.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "student_class",
        primaryKeys = ["studentCpf", "classId"],
        foreignKeys = [ForeignKey(
            entity = Student::class,
            parentColumns = ["cpf"],
            childColumns = ["studentCpf"],
            onDelete = CASCADE
        ), ForeignKey(
                entity = Class::class,
                parentColumns = ["id"],
                childColumns = ["classId"],
                onDelete = CASCADE
        )]
)
class StudentClass(
    var studentCpf: String,
    var classId: String,
    var missNumber: Int
) {

}