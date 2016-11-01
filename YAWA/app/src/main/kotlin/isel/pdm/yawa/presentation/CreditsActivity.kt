package isel.pdm.yawa.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import isel.pdm.yawa.R
import isel.pdm.yawa.models.WeatherInfo
import kotlinx.android.synthetic.main.activity_credits.*
import kotlinx.android.synthetic.main.toolbar_main.*

class CreditsActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_credits

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar_logo.setOnClickListener {
            val weatherInfo = intent.extras.getParcelable<WeatherInfo>("weather_info")
            startActivity(Intent(this,CreditsActivity::class.java).putExtra("weather_info",weatherInfo))
        }

        openweathermap_icon.setOnClickListener{
            val url = Uri.parse(resources.getString(R.string.api_link))
            startActivity(Intent(Intent.ACTION_VIEW, url))
        }
    }
}
