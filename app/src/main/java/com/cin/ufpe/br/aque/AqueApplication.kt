package com.cin.ufpe.br.aque

import android.app.Application
import android.content.Context
import android.content.Intent
import com.cin.ufpe.br.aque.actvities.MainActivity

class AqueApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: AqueApplication? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }
}