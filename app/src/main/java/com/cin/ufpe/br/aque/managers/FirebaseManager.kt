package com.cin.ufpe.br.aque.managers

import android.util.Log
import com.cin.ufpe.br.aque.models.Location
import com.cin.ufpe.br.aque.models.Student
import com.cin.ufpe.br.aque.models.UserLocation
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseManager {

    private lateinit var db: FirebaseFirestore
    private val TAG = FirebaseManager::class.java.simpleName

    constructor() {
        db = FirebaseFirestore.getInstance()
    }

    fun saveUserLocations(path: String, id: String, locations: List<Location>) {
        val userLocations = UserLocation(id, locations)

        db.collection(path)
            .add(userLocations)
            .addOnSuccessListener { documentReference ->
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
            .add(student)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Student ${student.cpf} saved on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving student", e)
            }
    }
}