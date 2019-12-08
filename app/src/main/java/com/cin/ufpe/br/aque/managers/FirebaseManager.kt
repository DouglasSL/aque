package com.cin.ufpe.br.aque.managers

import android.util.Log
import com.cin.ufpe.br.aque.data.Result
import com.cin.ufpe.br.aque.data.model.LoggedInUser
import com.cin.ufpe.br.aque.models.Location
import com.cin.ufpe.br.aque.models.Student
import com.cin.ufpe.br.aque.models.StudentLocation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class FirebaseManager {

    private lateinit var db: FirebaseFirestore
    private val TAG = FirebaseManager::class.java.simpleName

    constructor() {
        db = FirebaseFirestore.getInstance()
    }

    fun saveStudentLocations(studentId: String, className: String, day: Int, locations: List<Location>) {
        val studentLocations = StudentLocation(studentId, locations)

        db.collection("${className}_$day")
            .add(studentLocations)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Collected locations saved on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving collected locations", e)
            }
    }

    fun getStudentsLocations(studentId: String, className: String, day: Int) : List<StudentLocation> {
        var studentsLocation = mutableListOf<StudentLocation>()
        db.collection("${className}_$day")
            .get()
            .addOnSuccessListener { documents  ->
                for (document in documents) {
                    var studentLocation = document.toObject(StudentLocation::class.java)
                    studentsLocation.add(studentLocation)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        return studentsLocation
    }

    fun deleteStudentLocations(className: String, day: Int) {
        db.collection("${className}_$day")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                }
                Log.d(TAG, "Deleted student locations on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting student locations", e)
            }
    }

    fun saveStudent(student: Student) {
        db.collection("student")
            .document(student.email!!)
            .set(student)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Student ${student.email} saved on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving student", e)
            }
    }

    fun getStudent(email: String): Task<DocumentSnapshot> {
        return db.collection("student")
            .document(email)
            .get()
    }
}