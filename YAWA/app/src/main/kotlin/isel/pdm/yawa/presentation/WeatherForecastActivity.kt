package isel.pdm.yawa.presentation

import android.content.Intent
import android.os.Bundle
import isel.pdm.yawa.R
import isel.pdm.yawa.models.WeatherInfo
import kotlinx.android.synthetic.main.activity_weather_forecast.*
import kotlinx.android.synthetic.main.toolbar_main.*

class WeatherForecastActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_weather_forecast

    override fun localHandlerActivityExecution(weatherInfo: WeatherInfo) {
        startActivity(Intent(this,WeatherForecastActivity::class.java).putExtra("weatherInfo",weatherInfo))
    }

    private fun onWeatherInfoSelection(btn_id:Int, weather:WeatherInfo) {
        when(btn_id) {
            0 -> startActivity(Intent(this,WeatherCurrentActivity::class.java).putExtra("weather_info",weather))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weatherInfo = intent.extras.getParcelable<WeatherInfo>("weather_info")

        weather_forecast_selector.setOnCheckedChangeListener { radioGroup, i ->
            onWeatherInfoSelection(i,weatherInfo)
        }

        toolbar_logo.setOnClickListener {
            startActivity(Intent(this,CreditsActivity::class.java).putExtra("weather_info",weatherInfo))
        }
    }
}
