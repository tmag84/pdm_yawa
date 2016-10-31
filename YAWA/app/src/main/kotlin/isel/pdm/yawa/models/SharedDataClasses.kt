package isel.pdm.yawa.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Tiago on 23/10/2016.
 */

data class WeatherCoordinates(
        val lon: Double,
        val lat: Double
) : Parcelable {
    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = object : Parcelable.Creator<WeatherCoordinates> {
            override fun createFromParcel(source: Parcel) = WeatherCoordinates(source)
            override fun newArray(size: Int): Array<WeatherCoordinates?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            lon = source.readDouble(),
            lat = source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeDouble(lon)
            writeDouble(lat)
        }
    }
}

data class WeatherDescription(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
) : Parcelable {
    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = object : Parcelable.Creator<WeatherDescription> {
            override fun createFromParcel(source: Parcel) = WeatherDescription(source)
            override fun newArray(size: Int): Array<WeatherDescription?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            id = source.readInt(),
            main = source.readString(),
            description = source.readString(),
            icon = source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeInt(id)
            writeString(main)
            writeString(description)
            writeString(icon)
        }
    }
}
