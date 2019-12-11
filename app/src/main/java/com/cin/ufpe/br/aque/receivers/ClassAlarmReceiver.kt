package com.cin.ufpe.br.aque.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import com.cin.ufpe.br.aque.database.ClassDB
import com.cin.ufpe.br.aque.database.LocationDB
import com.cin.ufpe.br.aque.managers.AlarmManager
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import com.cin.ufpe.br.aque.models.Class
import com.cin.ufpe.br.aque.models.Location
import org.jetbrains.anko.doAsync

const val ACTION_START_CLASS_ALARM = "com.cin.ufpe.br.aque.services.action.START_CLASS_ALARM"
const val ACTION_END_CLASS_ALARM = "com.cin.ufpe.br.aque.services.action.END_CLASS_ALARM"
const val CODE_START_CLASS_ALARM = 1
const val C0DE_END_CLASS_ALARM = 2

class ClassAlarmReceiver : BroadcastReceiver() {

    private val TAG = ClassAlarmReceiver::class.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(ACTION_START_CLASS_ALARM)) {
            Log.i(TAG, "Start class alarm: requesting Alarm Manager to update locations")
            AlarmManager.setLocationAlarm(context)
            return
        }

        Log.i(TAG, "End class alarm: disabling current alarms")
        AlarmManager.cancelClassAlarm(context, CODE_START_CLASS_ALARM)
        AlarmManager.cancelClassAlarm(context, C0DE_END_CLASS_ALARM)

        var sharedPreferences = SharedPreferencesManager(context)
        var currentClass = sharedPreferences.getCurentClass()

        doAsync {
            var db = LocationDB.getDatabase(context)
            var locations =  db.LocationDAO().all()
            Log.i(TAG, "Retreived ${locations.size} locations during last class")

            var firebase = FirebaseManager()

            var userId = sharedPreferences.getUserId()
            if (!sharedPreferences.isStudent()){
                firebase.saveUserLocations("${currentClass.className}_${currentClass.day}", userId, locations)
            } else {
                firebase.saveUserLocations("${userId}_${currentClass.day}", userId, locations)
                AlarmManager.setMatcherAlarm(context, currentClass)
            }

            db.LocationDAO().clear()
        }

        doAsync {
            Log.i(TAG, "Checking for next class")

            var calendar = Calendar.getInstance()
            var currentDay = calendar.get(Calendar.DAY_OF_WEEK)
            var currentHour = calendar.get(Calendar.HOUR_OF_DAY)

            var db = ClassDB.getDatabase(context)
            val nextClass : Class? = db.ClassDAO().getNextClass(currentDay, currentHour)

            if (nextClass != null) {
                sharedPreferences.setCurentClass(nextClass)
                Log.i(TAG, "Setting next class start alarm")
                AlarmManager.setClassAlarm(context, nextClass.startHour,0, CODE_START_CLASS_ALARM, ACTION_START_CLASS_ALARM)
                Log.i(TAG, "Setting next class end alarm")
                AlarmManager.setClassAlarm(context, nextClass.endHour,0, C0DE_END_CLASS_ALARM, ACTION_END_CLASS_ALARM)
            }
        }
    }
}
