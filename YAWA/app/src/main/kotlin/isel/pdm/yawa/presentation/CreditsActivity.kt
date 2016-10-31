package isel.pdm.yawa.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import isel.pdm.yawa.R
import isel.pdm.yawa.models.WeatherInfo
import kotlinx.android.synthetic.main.activity_credits.*

class CreditsActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_credits

    override fun localHandlerActivityExecution(weatherInfo: WeatherInfo) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openweathermap_icon.setOnClickListener{
            val url = Uri.parse(resources.getString(R.string.api_link))
            startActivity(Intent(Intent.ACTION_VIEW, url))
        }
    }
}
