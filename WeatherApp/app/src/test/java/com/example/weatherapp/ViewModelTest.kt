package com.example.weatherapp

import android.location.Location
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Resource
import com.example.weatherapp.domain.weather.WeatherInfo
import com.example.weatherapp.presentation.WeatherState
import com.example.weatherapp.presentation.WeatherViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test

class ViewModelTest : StatefulViewModelTest() {

    private val mockRepository = mockk<WeatherRepository>()
    private val mockLocationTracker = mockk<LocationTracker>()
    private val mockLocation = mockk<Location>()


    /**
     * A test case to verify that, in the scenario that the server's data is successfully retrieved,
     * all screen states are expected.
     */
    @Test
    fun testApiSuccess() = run {
        val weatherInfo = WeatherInfo(
            temp = 298.48,
            description = "description"
        )
        coEvery { mockRepository.getWeatherInfo(38.16, -127.13, "") } returns
                FakeWeatherRepository().getWeatherInfo()
        coEvery { mockLocation.latitude } returns 38.16
        coEvery { mockLocation.longitude } returns -127.13
        coEvery { mockLocationTracker.getCurrentLocation() } returns mockLocation
        val viewModel = WeatherViewModel(mockRepository, mockLocationTracker)
        test(viewModel = viewModel,
            assertions = listOf(WeatherState(),
                WeatherState(isLoading = true),
                WeatherState(isLoading = false,
                    weatherInfo = weatherInfo))
        )
    }

    /**
     * A test scenario to verify that, in the event that data retrieval from the server
     * is unsuccessful, all screen states are expected.
     */
    @Test
    fun testApiFailure() = run {
        coEvery { mockRepository.getWeatherInfo(38.16, -127.13, "") } returns
                Resource.Error("")
        coEvery { mockLocation.latitude } returns 38.16
        coEvery { mockLocation.longitude } returns -127.13
        coEvery { mockLocationTracker.getCurrentLocation() } returns mockLocation
        val viewModel = WeatherViewModel(mockRepository, mockLocationTracker)
        test(viewModel = viewModel,
            assertions = listOf(WeatherState(),
                WeatherState(isLoading = true),
                WeatherState(isLoading = false,
                    error = ""))
        )
    }
}