package isel.pdm.yawa.presentation

import android.content.Intent
import android.os.Bundle
import isel.pdm.yawa.App
import isel.pdm.yawa.R
import isel.pdm.yawa.models.WeatherInfo
import isel.pdm.yawa.requests.handlers.GetWeatherInfo

class StartupActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_startup

    private fun localHandlerActivityExecution(weatherInfo: WeatherInfo) {
        if (weatherInfo.error!=null) {
            //doing nothing at the moment
        }

        if (weatherInfo.weatherCurrent!=null && weatherInfo.weatherForecast!=null) {
            val intent = Intent(this,WeatherCurrentActivity::class.java)
            startActivity(Intent(this,WeatherCurrentActivity::class.java).putExtra("weather_info",weatherInfo))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultCity : String = resources.getString(R.string.default_city)
        val defaultCountry : String = resources.getString(R.string.default_country_tag)

        GetWeatherInfo(
                application as App,
                {weatherInfo -> localHandlerActivityExecution(weatherInfo)},
                "$defaultCity,$defaultCountry"
        )

    }
}