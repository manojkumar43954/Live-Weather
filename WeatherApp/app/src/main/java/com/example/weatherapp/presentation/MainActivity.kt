package com.example.weatherapp.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.domain.util.DataStoreManager
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val LIMIT = 1
        const val APP_ID = BuildConfig.TEST_API_KEY
    }

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(this)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            lifecycleScope.launch {
                dataStoreManager.getLastSearchedName().collect {
                    if (it.isNotEmpty()) {
                        viewModel.getCityInfo(it, LIMIT, APP_ID)
                    } else {
                        viewModel.loadWeatherInfo(APP_ID)
                    }

                    setContent {
                        WeatherAppTheme {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color(getColor(R.color.purple_500)))
                            ) {
                                val searchText by viewModel.searchText.collectAsState()
                                val localFocusManager = LocalFocusManager.current

                                TextField(value = searchText,
                                    onValueChange = viewModel::onSearchTextChange,
                                    modifier = Modifier.fillMaxWidth(),
                                    placeholder = {
                                        Text(
                                            text = stringResource(
                                                id = R.string.search_by_city_name
                                            ).plus("...")
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            localFocusManager.clearFocus()
                                            viewModel.getCityInfo(searchText, LIMIT, APP_ID)
                                            lifecycleScope.launch {
                                                dataStoreManager.storeName(searchText)
                                            }
                                        }
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                val weatherState = viewModel.state.collectAsState().value
                                when {
                                    weatherState.isLoading -> {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator(color = Color.White)
                                        }
                                    }
                                    weatherState.error != null ||
                                            weatherState.isLocationNotFound -> {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                // This is the default message that appears in the event
                                                // of a network exception or when the server cannot be
                                                // reached to retrieve data. Sealed classes can be used
                                                // to verify API response data and network errors.
                                                text = stringResource(
                                                    id = R.string.error_message
                                                ),
                                                color = Color.White
                                            )
                                        }
                                    }
                                    else -> {
                                        WeatherCard(state = weatherState)
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}
