package com.cin.ufpe.br.aque.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import com.cin.ufpe.br.aque.models.UserLocation

const val EXTRA_CLASS_NAME = "class_name"

class MatcherAlarmReceiver : BroadcastReceiver() {

    private val TAG = MatcherAlarmReceiver::class.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK)
        var className = intent.getStringExtra(EXTRA_CLASS_NAME)

        var firebaseManager = FirebaseManager()
        var sharedPreferences = SharedPreferencesManager(context)
        firebaseManager.getUsersLocations("${className}_$currentDay")
            .addOnSuccessListener { documents  ->
                var studentsLocations = mutableListOf<UserLocation>()
                for (document in documents) {
                    var userLocation = document.toObject(UserLocation::class.java)
                    studentsLocations.add(userLocation)
                }

                firebaseManager.getUsersLocations("${sharedPreferences.getUserId()}_$currentDay")
                    .addOnSuccessListener { documents  ->
                        var teacherLocation = documents.first().toObject(UserLocation::class.java)
                        firebaseManager.deleteCollection("${className}_$currentDay")
                        firebaseManager.deleteCollection("${sharedPreferences.getUserId()}_$currentDay")

                        //match logic here



                    }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error getting teacher documents: ", exception)
                    }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting students documents: ", exception)
            }
    }
}