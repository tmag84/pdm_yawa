package isel.pdm.yawa.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Tiago on 24/10/2016.
 */

data class WeatherInfo(
        var weatherCurrent: WeatherCurrent? = null,
        var weatherForecast: WeatherForecast? = null,
        var error : String? = null
) : Parcelable {

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = object : Parcelable.Creator<WeatherInfo> {
            override fun createFromParcel(source: Parcel) = WeatherInfo(source)
            override fun newArray(size: Int): Array<WeatherInfo?> = arrayOfNulls(size)
        }
    }
    constructor(source: Parcel) : this(
            weatherCurrent = source.readTypedObject(WeatherCurrent.CREATOR),
            weatherForecast = source.readTypedObject(WeatherForecast.CREATOR),
            error = source.readString()
    )
    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeTypedObject(weatherCurrent, 0)
            writeTypedObject(weatherForecast, 0)
            writeString(error)
        }
    }
}