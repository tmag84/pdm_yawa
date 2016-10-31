package isel.pdm.yawa.utils

import android.content.res.Resources
import isel.pdm.yawa.R

/**
 * Created by Tiago on 29/10/2016.
 */

class UrlBuilder(res: Resources) {

    private val resources : Resources = res

    private fun buildWeatherUrl(location:String, language:String, path:String) : String {
        val baseUrl = resources.getString(R.string.api_url)
        val queryString = "${resources.getString(R.string.query_string)}$location"
        val langString = "${resources.getString(R.string.lang_string)}$language"
        val api_key = "${resources.getString(R.string.api_key_name)}=${resources.getString(R.string.api_key_value)}"
        return "$baseUrl$path?$queryString&$langString&$api_key"
    }

    fun buildWeatherCurrentUrl(location:String,language:String) : String {
        return buildWeatherUrl(location,language,resources.getString(R.string.api_current_weather))
    }

    fun buildWeatherForecastUrl(location:String,language:String) : String {
        return buildWeatherUrl(location,language,resources.getString(R.string.api_daily_forecast_weather))
    }

    fun buildWeatherImgUrl(imgId:String) : String {
        val imgUrl = resources.getString(R.string.api_img_icon)
        return "$imgUrl/$imgId.png"
    }
}