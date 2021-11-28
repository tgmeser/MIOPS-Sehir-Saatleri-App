package com.babyapps.citytimeapp.ui

import androidx.lifecycle.*
import com.babyapps.citytimeapp.data.City
import com.babyapps.citytimeapp.data.repository.CityRepository
import com.babyapps.citytimeapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val repository: CityRepository,
) :
    ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<City>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<City>>>
        get() = _dataState


    val cities = repository.getRecords().asLiveData()


    fun setStateEvent(citiesStateEvent: CitiesStateEvent) {
        viewModelScope.launch {
            when (citiesStateEvent) {
                is CitiesStateEvent.GetCitiesEvent -> {
                    repository.execute().onEach { dataState ->
                        _dataState.value = dataState
                    }.launchIn(viewModelScope)
                }
                is CitiesStateEvent.None -> {
                    // We do not need to do anything here!
                }
            }
        }
    }


    fun insert(city: City) =
        viewModelScope.launch {
            repository.insertCity(city)
        }

    fun update(city: City) =
        viewModelScope.launch {
            repository.updateCity(city)
        }

    fun delete(city: City) =
        viewModelScope.launch {
            repository.deleteCity(city)
        }

    fun unDo(city: City) = viewModelScope.launch {
        repository.unDoCity(city)
    }


}

sealed class CitiesStateEvent {
    object GetCitiesEvent : CitiesStateEvent()
    object None : CitiesStateEvent()
}
