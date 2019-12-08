package com.cin.ufpe.br.aque.managers

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager {
    companion object{
        fun checkLocationPermission(ctx: Context) : Boolean {
            return ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

        fun requestLocationPermission(ctx: Activity) {
            ActivityCompat.requestPermissions(
                ctx,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
    }

}