package com.cin.ufpe.br.aque.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager

const val EXTRA_CLASS_NAME = "class_name"

class MatcherAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK)
        var className = intent.getStringExtra(EXTRA_CLASS_NAME)

        var firebaseManager = FirebaseManager()
        var sharedPreferences = SharedPreferencesManager(context)
        var studentsLocations = firebaseManager.getUsersLocations("${className}_$currentDay")
        var teacherLocations = firebaseManager.getUsersLocations("${sharedPreferences.getUserId()}_$currentDay")
        firebaseManager.deleteCollection("${className}_$currentDay")
        firebaseManager.deleteCollection("${sharedPreferences.getUserId()}_$currentDay")

        //match logic
    }
}