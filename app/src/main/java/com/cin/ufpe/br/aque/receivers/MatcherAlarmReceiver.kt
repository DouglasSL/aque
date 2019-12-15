package com.cin.ufpe.br.aque.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import com.cin.ufpe.br.aque.managers.AlarmManager
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import com.cin.ufpe.br.aque.models.Date
import com.cin.ufpe.br.aque.models.Location
import com.cin.ufpe.br.aque.models.PresentStudents
import com.cin.ufpe.br.aque.models.UserLocation

const val EXTRA_CLASS_ID = "class_id"
const val EXTRA_CLASS_NAME = "class_name"

class MatcherAlarmReceiver : BroadcastReceiver() {

    private val TAG = MatcherAlarmReceiver::class.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "Matcher Alarm")
        val calendar = Calendar.getInstance()
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)
        var classId = intent.getStringExtra(EXTRA_CLASS_ID)
        var className = intent.getStringExtra(EXTRA_CLASS_NAME)

        var firebaseManager = FirebaseManager()
        var sharedPreferences = SharedPreferencesManager(context)
        firebaseManager.getUsersLocations("${classId}_$currentDayOfWeek")
            .addOnSuccessListener { documents  ->
                if (documents.size() == 0){
                    Log.i(TAG, "Empty documents - students")
                    return@addOnSuccessListener
                }
                Log.i(TAG, "Found students locations")
                var studentsLocations = mutableListOf<UserLocation>()
                for (document in documents) {
                    var userLocation = document.toObject(UserLocation::class.java)
                    studentsLocations.add(userLocation)
                }
                Log.i(TAG, "size: ${studentsLocations.size}")

                firebaseManager.getUsersLocations("${sharedPreferences.getUserId()}_$currentDayOfWeek")
                    .addOnSuccessListener { documents  ->
                        if (documents.size() == 0){
                            Log.i(TAG, "Empty documents - teacher")
                            return@addOnSuccessListener
                        }
                        Log.i(TAG, "Found teacher locations")
                        var teacherLocations = documents.first().toObject(UserLocation::class.java)
                        firebaseManager.deleteCollection("${classId}_$currentDayOfWeek")
                        firebaseManager.deleteCollection("${sharedPreferences.getUserId()}_$currentDayOfWeek")

                        //match logic
                        var locations = teacherLocations.locations
                        if (locations!!.size == 0) {
                            Log.i(TAG, "No locations collected")
                            return@addOnSuccessListener
                        }
                        var teacherLocation = locations!!.get(0)
                        var mostOccurrence = 0
                        for (i in locations) {
                            var occurrence = 0
                            for (j in locations) {
                                if (distance(i, j) <= 15){
                                    occurrence += 1
                                }
                            }
                            if (occurrence > mostOccurrence) {
                                teacherLocation = i
                                mostOccurrence = occurrence
                            }
                        }

                        var presentStudents = mutableListOf<String>()
                        for (studentLocation in studentsLocations) {
                            locations = studentLocation.locations
                            var occurrence = 0
                            for (location in locations!!) {
                                if (distance(teacherLocation, location) <= 25) {
                                    occurrence += 1
                                }
                            }

                            if (occurrence / locations!!.size >= 0.65) {
                                presentStudents.add(studentLocation.id!!)
                            }
                        }
                        Log.i(TAG, "Saving present students. Size: ${presentStudents.size}")

                        var docPath = "${classId}_${currentDay}_${currentMonth}_${currentYear}"
                        var pStudents = PresentStudents(currentDay, currentMonth, currentYear, presentStudents)
                        firebaseManager.savePresentStudents(docPath, pStudents)
                        firebaseManager.saveClassDate(classId, Date(currentDay, currentMonth, currentYear))

                        AlarmManager.cancelMatcherAlarm(context)
                    }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error getting teacher documents: ", exception)
                    }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting students documents: ", exception)
            }
    }

    private fun distance(location1: Location, location2: Location) : Double {
            var R = 6371 * 1000 // Earth's radius in m
            return Math.acos(Math.sin(location1.lat!!)*Math.sin(location2.lat!!) +
                    Math.cos(location1.lat!!)*Math.cos(location2.lat!!) *
                    Math.cos(location1.lng!!-location2.lng!!)) * R;
    }
}
