package isel.pdm.yawa.presentation

import android.content.Intent
import android.os.Bundle
import isel.pdm.yawa.R
import isel.pdm.yawa.models.WeatherInfo
import kotlinx.android.synthetic.main.activity_weather_current.*
import kotlinx.android.synthetic.main.toolbar_main.*

class WeatherCurrentActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_weather_current

    override fun localHandlerActivityExecution(weatherInfo: WeatherInfo) {
        startActivity(Intent(this,WeatherCurrentActivity::class.java).putExtra("weatherInfo",weatherInfo))
    }

    private fun onWeatherInfoSelection(btn_id:Int, weather:WeatherInfo) {
        when(btn_id) {
            1 -> startActivity(Intent(this,WeatherForecastActivity::class.java).putExtra("weather_info",weather))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherInfo = intent.extras.getParcelable<WeatherInfo>("weather_info")
        textId.text = weatherInfo.weatherCurrent?.name

        weather_current_selector.setOnCheckedChangeListener { radioGroup, i ->
            onWeatherInfoSelection(i,weatherInfo)
        }

        //toolbar_input.setOnKeyListener { view, i, keyEvent ->  }

        toolbar_logo.setOnClickListener {
            startActivity(Intent(this,CreditsActivity::class.java).putExtra("weather_info",weatherInfo))
        }
    }
}
