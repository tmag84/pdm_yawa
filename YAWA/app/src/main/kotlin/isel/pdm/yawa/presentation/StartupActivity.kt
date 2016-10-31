package isel.pdm.yawa.presentation

import android.content.Intent
import android.os.Bundle
import isel.pdm.yawa.R
import isel.pdm.yawa.models.WeatherInfo

class StartupActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_startup

    override fun localHandlerActivityExecution(weatherInfo: WeatherInfo) {
        startActivity(Intent(this,WeatherCurrentActivity::class.java).putExtra("weather_info",weatherInfo))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val defaultCity : String = resources.getString(R.string.default_city)
        val defaultCountry : String = resources.getString(R.string.default_country_tag)
        getWeatherInfo("$defaultCity,$defaultCountry")
    }
}