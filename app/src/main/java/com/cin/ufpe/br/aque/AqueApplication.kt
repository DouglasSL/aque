package com.cin.ufpe.br.aque

import android.app.Application
import android.content.Intent
import com.cin.ufpe.br.aque.actvities.MainActivity

class AqueApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startActivity(Intent(applicationContext, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}