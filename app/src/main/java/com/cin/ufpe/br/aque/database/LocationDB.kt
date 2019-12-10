package com.cin.ufpe.br.aque.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cin.ufpe.br.aque.dao.LocationDAO
import com.cin.ufpe.br.aque.models.Location

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class LocationDB : RoomDatabase(){
    abstract fun LocationDAO(): LocationDAO

    companion object {
        private var INSTANCE : LocationDB? = null
        fun getDatabase(ctx : Context) : LocationDB {
            if (INSTANCE == null) {
                synchronized(LocationDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        LocationDB::class.java,
                        "location.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}