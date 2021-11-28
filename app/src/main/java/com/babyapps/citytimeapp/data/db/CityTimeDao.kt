package com.babyapps.citytimeapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.babyapps.citytimeapp.data.City
import kotlinx.coroutines.flow.Flow


@Dao
interface CityTimeDao {

    @Query("SELECT * FROM city_table")
    fun getAllCities(): Flow<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City)

    @Update
    suspend fun update(city: City)

    @Delete
    suspend fun delete(city: City)
}