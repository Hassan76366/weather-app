package com.example.weather_application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_application.api.Constant
import com.example.weather_application.api.NetworkResponse
import com.example.weather_application.api.RetrofitInstance
import com.example.weather_application.api.WeatherModel
import kotlinx.coroutines.launch


class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String) {
        viewModelScope.launch {

            try {
                val response = weatherApi.getWeather(Constant.apiKey, city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")

                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("failure !!")
            }
        }


    }
}
