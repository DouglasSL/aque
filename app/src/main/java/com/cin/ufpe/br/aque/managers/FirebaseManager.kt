package com.cin.ufpe.br.aque.managers

import android.util.Log
import com.cin.ufpe.br.aque.models.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

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

    fun saveClassDescription(classDescription: ClassDescription) {
        db.collection("class_description")
            .add(classDescription)
            .addOnSuccessListener {
                Log.d(TAG, "Saved class ${classDescription.className} on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving class ${classDescription.className}", e)
            }
    }

    fun saveUserClass(path: String, userClass: UserClass) {
        db.collection(path)
            .add(userClass)
            .addOnSuccessListener {
                Log.d(TAG, "Saved userClass  on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving userClass", e)
            }
    }

    fun getClassDescription() : Task<QuerySnapshot>{
        return db.collection("class_description").get()
    }

    fun getUsersLocations(path: String) : Task<QuerySnapshot> {
        return db.collection(path).get()
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