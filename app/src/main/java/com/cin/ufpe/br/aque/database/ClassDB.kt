package com.cin.ufpe.br.aque.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cin.ufpe.br.aque.dao.ClassDAO
import com.cin.ufpe.br.aque.models.Class

@Database(entities = [Class::class], version = 1, exportSchema = false)
abstract class ClassDB : RoomDatabase(){
    abstract fun ClassDAO(): ClassDAO

    companion object {
        private var INSTANCE : ClassDB? = null
        fun getDatabase(ctx : Context) : ClassDB {
            if (INSTANCE == null) {
                synchronized(ClassDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        ClassDB::class.java,
                        "class.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}