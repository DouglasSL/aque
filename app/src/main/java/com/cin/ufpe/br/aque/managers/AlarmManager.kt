package com.cin.ufpe.br.aque.managers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import com.cin.ufpe.br.aque.receivers.ClassAlarmReceiver
import com.cin.ufpe.br.aque.receivers.RoutineAlarmReceiver

class AlarmManager {
    companion object {
        fun setRoutineAlarm(ctx : Context) {
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 6)
            }

            val alarmManager: AlarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent: PendingIntent = Intent(ctx, RoutineAlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(ctx, 0, intent, 0)
            }
            alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )
        }

        fun setClassAlarm(ctx: Context, hour: Int, minute: Int, code: Int, action: String) {
            val alarmManager: AlarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent: PendingIntent = Intent(ctx, ClassAlarmReceiver::class.java).let { intent ->
                intent.setAction(action)
                PendingIntent.getBroadcast(ctx, code, intent, 0)
            }

            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }

            alarmManager.set(
                AlarmManager.ELAPSED_REALTIME,
                calendar.timeInMillis,
                alarmIntent
            )
        }

        fun cancelClassAlarm(ctx: Context, code: Int){
            val alarmManager: AlarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent: PendingIntent = Intent(ctx, ClassAlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(ctx, code, intent, 0)
            }
            alarmManager.cancel(alarmIntent)
        }
    }
}