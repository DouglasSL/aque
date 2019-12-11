package com.cin.ufpe.br.aque.managers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.SystemClock
import android.util.Log
import com.cin.ufpe.br.aque.receivers.*

class AlarmManager {
    companion object {
        private val TAG = AlarmManager::class.simpleName

        fun setRoutineAlarm(ctx : Context) {
            Log.i(TAG, "Setting alarm to wake up everyday at 6am")
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 6)
            }
            setRepeatAlarm(ctx, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, RoutineAlarmReceiver::class.java)
        }

        fun setLocationAlarm(ctx: Context) {
            Log.i(TAG, "Setting up location alarm to wake every fifteen minutes")
            setRepeatAlarm(ctx, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, LocationAlarmReceiver::class.java)
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

        fun setMatcherAlarm(ctx: Context, currentClass: com.cin.ufpe.br.aque.models.Class) {
            val alarmManager: AlarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent: PendingIntent = Intent(ctx, MatcherAlarmReceiver::class.java).let { intent ->
                intent.putExtra(EXTRA_CLASS_ID, currentClass.classId)
                intent.putExtra(EXTRA_CLASS_NAME, currentClass.className)
                PendingIntent.getBroadcast(ctx, 0, intent, 0)
            }

            alarmManager.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 2 * 60 * 1000,
                alarmIntent
            )
        }

        private fun setRepeatAlarm(ctx: Context, startTime: Long, interval: Long, receiver: Class<out BroadcastReceiver>) {
            val alarmManager: AlarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent: PendingIntent = Intent(ctx, receiver::class.java).let { intent ->
                PendingIntent.getBroadcast(ctx, 0, intent, 0)
            }

            alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                startTime,
                interval,
                alarmIntent
            )
        }

        fun cancelClassAlarm(ctx: Context, code: Int){
            Log.i(TAG, "Disabling class alarm")
            val alarmManager: AlarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent: PendingIntent = Intent(ctx, ClassAlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(ctx, code, intent, 0)
            }
            alarmManager.cancel(alarmIntent)
        }

        fun cancelMatcherAlarm(ctx: Context){
            Log.i(TAG, "Disabling matcher alarm")
            val alarmManager: AlarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent: PendingIntent = Intent(ctx, MatcherAlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(ctx, 0, intent, 0)
            }
            alarmManager.cancel(alarmIntent)
        }
    }
}