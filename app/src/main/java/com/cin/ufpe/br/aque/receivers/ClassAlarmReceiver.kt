package com.cin.ufpe.br.aque.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.location.LocationProvider
import com.cin.ufpe.br.aque.managers.AlarmManager

const val ACTION_START_CLASS_ALARM = "com.cin.ufpe.br.aque.services.action.START_CLASS_ALARM"
const val ACTION_END_CLASS_ALARM = "com.cin.ufpe.br.aque.services.action.END_CLASS_ALARM"
const val CODE_START_CLASS_ALARM = 1
const val C0DE_END_CLASS_ALARM = 2

class ClassAlarmReceiver : BroadcastReceiver() {

    // private var batch events

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(ACTION_START_CLASS_ALARM)) {
            // collect wifi and location, save in batch
            return
        }

        // send request with batch
        AlarmManager.cancelClassAlarm(context, CODE_START_CLASS_ALARM)
        AlarmManager.cancelClassAlarm(context, C0DE_END_CLASS_ALARM)

        //check for next class
    }
}
