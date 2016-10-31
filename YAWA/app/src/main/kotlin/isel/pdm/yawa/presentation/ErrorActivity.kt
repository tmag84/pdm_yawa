package isel.pdm.yawa.presentation

import android.os.Bundle
import isel.pdm.yawa.R
import isel.pdm.yawa.models.WeatherInfo

class ErrorActivity : BaseActivity() {

    override val layoutResId: Int = R.id.activity_error

    override fun localHandlerActivityExecution(weatherInfo: WeatherInfo) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
