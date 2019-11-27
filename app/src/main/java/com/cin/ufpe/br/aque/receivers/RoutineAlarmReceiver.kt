package com.cin.ufpe.br.aque.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import com.cin.ufpe.br.aque.managers.AlarmManager


class RoutineAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK)
        // check from db if the current day has any class and get the next class (create a manager for this part)
        AlarmManager.setClassAlarm(context, 10,0, CODE_START_CLASS_ALARM, ACTION_START_CLASS_ALARM)
        AlarmManager.setClassAlarm(context, 12,0, C0DE_END_CLASS_ALARM, ACTION_END_CLASS_ALARM)
    }

}