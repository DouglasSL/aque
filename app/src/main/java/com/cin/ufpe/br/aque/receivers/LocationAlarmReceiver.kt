package com.cin.ufpe.br.aque.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cin.ufpe.br.aque.managers.LocationManager

class LocationAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var locationManager = LocationManager()
        locationManager.requestLocation(context)
    }
}
