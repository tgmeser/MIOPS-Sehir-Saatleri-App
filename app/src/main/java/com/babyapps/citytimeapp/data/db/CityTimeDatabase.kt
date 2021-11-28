package com.babyapps.citytimeapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.babyapps.citytimeapp.data.City

@Database(entities = [City::class],version = 1)
abstract class CityTimeDatabase: RoomDatabase() {
    abstract fun dao(): CityTimeDao
}