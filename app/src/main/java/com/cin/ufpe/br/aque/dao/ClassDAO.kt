package com.cin.ufpe.br.aque.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cin.ufpe.br.aque.models.Class

@Dao
interface ClassDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(c : Class)

    @Query("SELECT EXISTS(SELECT * FROM class WHERE day = :day)")
    fun hasClass(day: Int) : Boolean

    @Query("SELECT * FROM class WHERE day = :day ORDER BY startHour LIMIT 1")
    fun getFirstClass(day: Int) : Class

    @Query("SELECT * FROM class WHERE day = :day AND startHour >= :hour LIMIT 1")
    fun getNextClass(day: Int, hour: Int) : Class?
}