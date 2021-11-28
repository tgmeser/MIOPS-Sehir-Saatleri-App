package com.babyapps.citytimeapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.babyapps.citytimeapp.data.City
import com.babyapps.citytimeapp.data.db.CityTimeDao
import com.babyapps.citytimeapp.data.remote.CityTimeApi
import com.babyapps.citytimeapp.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val api: CityTimeApi,
    private val dao: CityTimeDao
) {

    // Db Operations - dao
    //val getSavedCities: LiveData<List<City>> = dao.getAllCities().asLiveData()
    fun getRecords() = dao.getAllCities()

    suspend fun insertCity(city: City) = dao.insert(city)

    suspend fun deleteCity(city: City) = dao.delete(city)

    suspend fun updateCity(city: City) = dao.update(city)

    suspend fun unDoCity(city: City)=dao.insert(city)


    // Network Operations - api
    suspend fun execute(): Flow<DataState<List<City>>> = flow {
        emit(DataState.Loading)
        delay(1000L)
        val cities = api.getCities()
        emit(DataState.Success(cities))

    }

}