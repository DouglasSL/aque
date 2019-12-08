package com.cin.ufpe.br.aque.managers

import android.util.Log
import com.cin.ufpe.br.aque.models.Location
import com.cin.ufpe.br.aque.models.Student
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseManager {

    private lateinit var db: FirebaseFirestore
    private val TAG = FirebaseManager::class.java.simpleName

    constructor() {
        db = FirebaseFirestore.getInstance()
    }

    fun saveStudentLocations(studentId: String, className: String, day: Int, locations: List<Location>) {
        val classLocations = hashMapOf(
            "student" to studentId,
            "class" to className,
            "day" to day,
            "locations" to locations
        )

        db.collection("class_locations")
            .add(classLocations)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Collected locations saved on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving collected locations", e)
            }
    }

    fun saveStudent(student: Student) {
        db.collection("student")
            .add(student)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Student ${student.cpf} saved on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving student", e)
            }
    }

    fun getStudentsLocations(studentId: String, className: String, day: Int) {
        db.collection("class_locations")
            .whereEqualTo("student", studentId)
            .whereEqualTo("class", className)
            .whereEqualTo("day", day).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    //TODO
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}