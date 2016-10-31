package isel.pdm.yawa.models

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Tiago on 23/10/2016.
 */

data class WeatherCurrent(
        val coord: WeatherCoordinates,
        val weather: List<WeatherDescription>,
        val base: String,
        val main: WeatherMain,
        val wind: WeatherWind,
        val clouds: WeatherClouds,
        val dt: Long,
        val sys: WeatherSys,
        val id: Int,
        val name: String,
        val cod: String
) : Parcelable {
    data class WeatherMain(
            val temp: Double,
            val pressure: Double,
            val humidity: Double,
            @JsonProperty("temp_min") val tempMin: Double,
            @JsonProperty("temp_max") val tempMax: Double,
            @JsonProperty("sea_level") val seaLevel: Double,
            @JsonProperty("grnd_level") val grndLevel: Double
    ) : Parcelable {
        companion object {
            @JvmField @Suppress("unused")
            val CREATOR = object : Parcelable.Creator<WeatherMain> {
                override fun createFromParcel(source: Parcel) = WeatherMain(source)
                override fun newArray(size: Int): Array<WeatherMain?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(
                temp = source.readDouble(),
                pressure = source.readDouble(),
                humidity = source.readDouble(),
                tempMin = source.readDouble(),
                tempMax = source.readDouble(),
                seaLevel = source.readDouble(),
                grndLevel = source.readDouble()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.apply {
                writeDouble(temp)
                writeDouble(pressure)
                writeDouble(humidity)
                writeDouble(tempMin)
                writeDouble(tempMax)
                writeDouble(seaLevel)
                writeDouble(grndLevel)
            }
        }
    }

    data class WeatherWind(
            val speed: Double,
            val deg: Double
    ) : Parcelable {
        companion object {
            @JvmField @Suppress("unused")
            val CREATOR = object : Parcelable.Creator<WeatherWind> {
                override fun createFromParcel(source: Parcel) = WeatherWind(source)
                override fun newArray(size: Int): Array<WeatherWind?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(
                speed = source.readDouble(),
                deg = source.readDouble()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.apply {
                writeDouble(speed)
                writeDouble(deg)
            }
        }
    }

    data class WeatherClouds(
            val all: Int
    ) : Parcelable {
        companion object {
            @JvmField @Suppress("unused")
            val CREATOR = object : Parcelable.Creator<WeatherClouds> {
                override fun createFromParcel(source: Parcel) = WeatherClouds(source)
                override fun newArray(size: Int): Array<WeatherClouds?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(
                all = source.readInt()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.apply {
                writeInt(all)
            }
        }
    }

    data class WeatherSys(
            val message: Double,
            val country: String,
            val sunrise: Long,
            val sunset: Long
    ) : Parcelable {
        companion object {
            @JvmField @Suppress("unused")
            val CREATOR = object : Parcelable.Creator<WeatherSys> {
                override fun createFromParcel(source: Parcel) = WeatherSys(source)
                override fun newArray(size: Int): Array<WeatherSys?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(
                message = source.readDouble(),
                country = source.readString(),
                sunrise = source.readLong(),
                sunset = source.readLong()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.apply {
                writeDouble(message)
                writeString(country)
                writeLong(sunrise)
                writeLong(sunset)
            }
        }
    }

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = object : Parcelable.Creator<WeatherCurrent> {
            override fun createFromParcel(source: Parcel) = WeatherCurrent(source)
            override fun newArray(size: Int): Array<WeatherCurrent?> = arrayOfNulls(size)
        }
    }
    constructor(source: Parcel) : this(
            coord = source.readTypedObject(WeatherCoordinates.CREATOR),
            weather = mutableListOf<WeatherDescription>().apply { source.readTypedList(this, WeatherDescription.CREATOR) },
            base = source.readString(),
            main = source.readTypedObject(WeatherMain.CREATOR),
            wind = source.readTypedObject(WeatherWind.CREATOR),
            clouds = source.readTypedObject(WeatherClouds.CREATOR),
            dt = source.readLong(),
            sys = source.readTypedObject(WeatherSys.CREATOR),
            id = source.readInt(),
            name = source.readString(),
            cod = source.readString()
    )
    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeTypedObject(coord,0)
            writeTypedList(weather)
            writeString(base)
            writeTypedObject(main,0)
            writeTypedObject(clouds,0)
            writeLong(dt)
            writeTypedObject(sys,0)
            writeInt(id)
            writeString(name)
            writeString(cod)
        }
    }
}