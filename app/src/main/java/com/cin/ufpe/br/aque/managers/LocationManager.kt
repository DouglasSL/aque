package com.cin.ufpe.br.aque.managers

import android.content.Context
import android.location.LocationManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log

class LocationManager : LocationListener {

    fun requestLocation(ctx : Context) {
        val locationManager: LocationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (PermissionManager.checkLocationPermission(ctx)) {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null)
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null)
        }
    }

    override fun onLocationChanged(location: Location?) {
        Log.e("TAG", location.toString())
        // salvar location no banco
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }


}