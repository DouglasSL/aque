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
            .document(classDescription.id!!)
            .set(classDescription)
            .addOnSuccessListener {
                Log.d(TAG, "Saved class ${classDescription.className} on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving class ${classDescription.className}", e)
            }
    }

    fun saveUserClass(path: String, userClass: UserClass) {
        db.collection(path)
            .document(userClass.classId!!)
            .set(userClass)
            .addOnSuccessListener {
                Log.d(TAG, "Saved userClass  on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving userClass", e)
            }

        db.collection(path+"$"+userClass.userId)
            .add(userClass)
    }

    fun getClassDescription(classId: String) : Task<DocumentSnapshot>{
        return db.collection("class_description").document(classId).get()
    }

    fun getUserClasses(path: String, userId: String): Task<QuerySnapshot> {
        return db.collection("$path$$userId").get()
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
            .addOnSuccessListener {
                Log.d(TAG, "Student ${student.email} saved on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving student", e)
            }
    }

    fun savePresentStudents(docPath: String, presentStudents: PresentStudents) {
        db.collection("present_students")
            .document(docPath)
            .set(presentStudents)
            .addOnSuccessListener {
                Log.d(TAG, "Present students of $docPath saved on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving Present students of $docPath", e)
            }
    }

    fun getPresentStudents(docPath: String) : Task<DocumentSnapshot> {
        return db.collection("present_students")
            .document(docPath)
            .get()
    }

    fun saveClassDate(classId: String, date: Date) {
        db.collection("${classId}_date")
            .add(date)
            .addOnSuccessListener {
                Log.d(TAG, "Class Date saved on Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error saving Class Date", e)
            }
    }

    fun getClassDate(classId: String): Task<QuerySnapshot> {
        return db.collection("${classId}_date").get()
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
            .addOnSuccessListener {
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