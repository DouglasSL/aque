package com.cin.ufpe.br.aque.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import com.cin.ufpe.br.aque.database.ClassDB
import com.cin.ufpe.br.aque.managers.AlarmManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import org.jetbrains.anko.doAsync


class RoutineAlarmReceiver : BroadcastReceiver() {

    private val TAG = RoutineAlarmReceiver::class.simpleName

    override fun onReceive(context: Context, intent: Intent?) {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK)
        
        doAsync {
            Log.i(TAG, "Checking for classes")
            val db = ClassDB.getDatabase(context)
            if (db.ClassDAO().hasClass(currentDay)) {
                Log.i(TAG, "Class found for day: $currentDay")

                var firstClass = db.ClassDAO().getFirstClass(currentDay)
                var sharedPreferences = SharedPreferencesManager(context)
                sharedPreferences.setCurentClass(firstClass)

                Log.i(TAG, "Setting start class alarm")
                AlarmManager.setClassAlarm(context, firstClass.startHour,0, CODE_START_CLASS_ALARM, ACTION_START_CLASS_ALARM)
                Log.i(TAG, "Setting end class alarm")
                AlarmManager.setClassAlarm(context, firstClass.endHour,0, C0DE_END_CLASS_ALARM, ACTION_END_CLASS_ALARM)
            }
        }
    }
}