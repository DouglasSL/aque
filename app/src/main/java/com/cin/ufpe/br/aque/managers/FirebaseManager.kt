package com.cin.ufpe.br.aque.managers

import android.util.Log
import com.cin.ufpe.br.aque.models.Location
import com.cin.ufpe.br.aque.models.Professor
import com.cin.ufpe.br.aque.models.Student
import com.cin.ufpe.br.aque.models.UserLocation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseManager {

    private var db: FirebaseFirestore
    private val TAG = FirebaseManager::class.java.simpleName

    constructor() {
        db = FirebaseFirestore.getInstance()
    }

    fun saveUserLocations(path: String, id: String, locations: List<Location>) {
        val userLocations = UserLocation(id, locations)

        db.collection(path)
            .add(userLocations)
            .addOnSuccessListener {
                Log.d(TAG, "Collected locations saved on Firebase on path ${path}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving collected locations", e)
            }
    }

    fun getUsersLocations(path: String) : List<UserLocation> {
        var usersLocations = mutableListOf<UserLocation>()
        db.collection(path)
            .get()
            .addOnSuccessListener { documents  ->
                for (document in documents) {
                    var userLocation = document.toObject(UserLocation::class.java)
                    usersLocations.add(userLocation)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        return usersLocations
    }

    fun deleteCollection(path: String) {
        db.collection(path)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                }
                Log.d(TAG, "Deleted path $path on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting path $path :", e)
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

    fun saveProfessor(professor: Professor) {
        db.collection("professor")
            .document(professor.email!!)
            .set(professor)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Professor ${professor.email} saved on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving professor", e)
            }
    }

    fun getProfessor(email: String): Task<DocumentSnapshot> {
        return db.collection("professor")
            .document(email)
            .get()
    }
}