package com.cin.ufpe.br.aque.managers

import android.content.Context
import android.location.LocationManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import com.cin.ufpe.br.aque.AqueApplication
import com.cin.ufpe.br.aque.database.LocationDB
import org.jetbrains.anko.doAsync

class LocationManager : LocationListener {

    private val TAG = com.cin.ufpe.br.aque.managers.LocationManager::class.java.simpleName

    fun requestLocation(ctx : Context) {
        Log.i(TAG, "Requesting Location Update")
        val locationManager: LocationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (PermissionManager.checkLocationPermission(ctx)) {
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null)
        } else {
            Log.e(TAG, "Location Permission denied")
        }
    }

    override fun onLocationChanged(location: Location?) {
        Log.i(TAG, "Location received")
        if (location != null) {
            Log.i(TAG, "Current location is: $location")
            var lat = location.latitude
            var long = location.longitude
            var loc = com.cin.ufpe.br.aque.models.Location(null, lat, long)
            doAsync {
                val db = LocationDB.getDatabase(AqueApplication.applicationContext())
                db.LocationDAO().add(loc)
                Log.i(TAG, "Location saved on database")
            }
        }
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }


}