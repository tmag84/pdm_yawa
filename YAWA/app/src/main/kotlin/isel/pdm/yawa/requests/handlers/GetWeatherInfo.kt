package isel.pdm.yawa.requests.handlers

import com.android.volley.VolleyError
import isel.pdm.yawa.App
import isel.pdm.yawa.models.WeatherCurrent
import isel.pdm.yawa.models.WeatherForecast
import isel.pdm.yawa.models.WeatherInfo
import isel.pdm.yawa.requests.GetRequest
import java.util.*

/**
 * Created by Tiago on 01/11/2016.
 *
 * this class is responsible for handling volley requests for weather information to a specific city
 *
 */

class GetWeatherInfo(app: App, func:(weatherInfo:WeatherInfo) -> Unit, locationString:String)  {
    val myFunc = func

    init {
        val requestQueue = app.requestQueue
        val urlBuilder = app.urlBuilder
        val language = Locale.getDefault().language
        val weatherInfo = WeatherInfo()

        //request for current weather information
        requestQueue.add(
                GetRequest(
                        urlBuilder.buildWeatherCurrentUrl(locationString,language),
                        WeatherCurrent::class.java,
                        {result->handleWeatherCurrentResult(result,weatherInfo)},
                        {error->handleError(error,weatherInfo)}
                )
        )

        //request for daily forecast information
        requestQueue.add(
                GetRequest(
                        urlBuilder.buildWeatherForecastUrl(locationString,language),
                        WeatherForecast::class.java,
                        {result->handleWeatherForecastResult(result,weatherInfo)},
                        {error->handleError(error,weatherInfo)}
                )
        )
    }

    private fun handleError(error:VolleyError, weatherInfo: WeatherInfo) {
        weatherInfo.error=error.message
        myFunc.invoke(weatherInfo)
    }

    private fun handleWeatherCurrentResult(data:WeatherCurrent, weatherInfo:WeatherInfo) {
        weatherInfo.weatherCurrent=data
        myFunc.invoke(weatherInfo)
    }

    private fun handleWeatherForecastResult(data:WeatherForecast, weatherInfo:WeatherInfo) {
        weatherInfo.weatherForecast=data
        myFunc.invoke(weatherInfo)
    }
}
