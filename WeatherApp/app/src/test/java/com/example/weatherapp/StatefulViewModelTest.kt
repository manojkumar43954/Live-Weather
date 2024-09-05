package com.example.weatherapp

import androidx.lifecycle.viewModelScope
import com.example.weatherapp.presentation.WeatherState
import com.example.weatherapp.presentation.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

abstract class StatefulViewModelTest{

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    protected fun test(
        viewModel: WeatherViewModel,
        assertions: List<WeatherState>): Unit = runTest {
        val states : MutableList<WeatherState> = mutableListOf()
        val stateCollectionJob = viewModel.viewModelScope.launch {
            viewModel.state.toList(states)
        }
        viewModel.loadWeatherInfo("")
        assert(assertions.size == states.size)
        assertions.zip(states) { assertion, state ->
            assert(assertion == state)
        }
        stateCollectionJob.cancel()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanUp(){
        Dispatchers.resetMain()
    }
}