package com.example.weatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private var _mutableStateFlow = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _mutableStateFlow.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    /**
     * Retrieves weather info from the server
     * If data cannot be found, it returns an error message to the user.
     */
    fun loadWeatherInfo(appId: String) {
        viewModelScope.launch {
            _mutableStateFlow.value = WeatherState( isLoading = true,
                error = null)
            locationTracker.getCurrentLocation()?.let { location ->
                getWeatherInfo(location.latitude, location.longitude, appId)
            } ?: run {
                _mutableStateFlow.value = WeatherState( isLoading = false,
                    isLocationNotFound = true)
            }
        }
    }

    /**
     * Retrieves city info from the server
     * If data cannot be found, it returns an error message to the user.
     * @param name user entered city or state or country name
     */
    fun getCityInfo(name : String, limit: Int, appId: String) {
        viewModelScope.launch {
            _mutableStateFlow.value = WeatherState( isLoading = false,
                error = null)
            val result = repository.getCityInfo(name, limit, appId)
            val cityInfo = result.data
            if (cityInfo?.lat != null && cityInfo.long != null) {
                getWeatherInfo(cityInfo.lat, cityInfo.long, appId)
            } else {
                _mutableStateFlow.value = WeatherState(isLoading = false,
                    error = result.message)
            }
        }
    }

    private fun getWeatherInfo(lat: Double, long: Double, appId: String) {
        viewModelScope.launch {
            when (val result = repository.getWeatherInfo(
                lat,
                long,
                appId
            )) {
                is Resource.Success -> {
                    _mutableStateFlow.value = WeatherState(
                        weatherInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    _mutableStateFlow.value = WeatherState(
                        weatherInfo = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}