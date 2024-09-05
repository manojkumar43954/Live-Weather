package com.example.weatherapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.padding(16.dp)
    ) {
        state.weatherInfo?.let { info ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val url = BuildConfig.CDN+ info.icon + "@2x.png"
                val painter = rememberAsyncImagePainter(url)
                Image(painter = painter,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "${info.temp}°C",
                    fontSize =  50.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = info.description?: "",
                    fontSize = 20.sp,
                    color = Color.Black)
                Spacer(modifier = Modifier.height(32.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    WeatherInfoDisplay(
                        value = info.windSpeed?.roundToInt(),
                        unit = "km/h" ,
                        icon = ImageVector.vectorResource(id = R.drawable.baseline_wind_power_24) ,
                        textStyle = TextStyle(color = Color.Black)
                    )

                    WeatherInfoDisplay(
                        value = info.humidity,
                        unit = "%" ,
                        icon = ImageVector.vectorResource(id = R.drawable.baseline_whatshot_24) ,
                        textStyle = TextStyle(color = Color.Black)
                    )

                    WeatherInfoDisplay(
                        value = info.windGust?.roundToInt(),
                        unit = "km/h" ,
                        icon = ImageVector.vectorResource(id = R.drawable.baseline_speed_24) ,
                        textStyle = TextStyle(color = Color.Black)
                    )
                }
            }
        }
    }
}