package com.cin.ufpe.br.aque.utils

import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import com.cin.ufpe.br.aque.database.ClassDB
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.models.Class
import com.cin.ufpe.br.aque.models.ClassDescription
import com.cin.ufpe.br.aque.models.UserClass
import org.jetbrains.anko.doAsync

class Utils {
    companion object{
        private val TAG = Utils::class.simpleName

        fun checkForClass(ctx: Context, path: String, email: String) {
            Log.e(TAG, "Checking for classes")
            doAsync {
                var db = ClassDB.getDatabase(ctx)
                var classes = db.ClassDAO().all()
                if (classes.isEmpty()) {
                    Log.e(TAG, "There are no classes on local db, checking on firebase")
                    var firebaseManager = FirebaseManager()
                    firebaseManager.getUserClasses(path, email).addOnSuccessListener { documents  ->
                        if (documents.isEmpty) {
                            return@addOnSuccessListener
                        }
                        Log.e(TAG, "Found classes on firebase")
                        for (document in documents) {
                            var userClass = document.toObject(UserClass::class.java)
                            firebaseManager.getClassDescription(userClass.classId!!).addOnSuccessListener { classDescDoc ->
                                var classDesc = classDescDoc.toObject(ClassDescription::class.java)
                                db.ClassDAO().add(Class(0, classDesc?.id!!,classDesc?.className!!, classDesc?.firstDay!!, classDesc?.firstDayStartHour!!,classDesc?.firstDayEndHour!!))
                                db.ClassDAO().add(Class(0, classDesc?.id!!,classDesc?.className!!, classDesc?.secondDay!!, classDesc?.secondDayStartHour!!,classDesc?.secondDayEndHour!!))
                            }.addOnFailureListener { exception ->

                                Log.e(TAG, "Error getting class description $exception")
                            }
                        }
                    }.addOnFailureListener { exception ->
                        Log.e(TAG, "Error getting documents $exception")
                    }
                }
            }
        }
    }
}