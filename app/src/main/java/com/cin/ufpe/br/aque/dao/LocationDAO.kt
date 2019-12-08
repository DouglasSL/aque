package com.cin.ufpe.br.aque.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cin.ufpe.br.aque.models.Location

@Dao
interface LocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(loc : Location)

    @Query("SELECT * FROM location")
    fun all() : List<Location>

    @Query("DELETE FROM location")
    fun clear()
}