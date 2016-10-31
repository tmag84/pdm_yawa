package isel.pdm.yawa.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Tiago on 23/10/2016.
 */

data class WeatherForecast(
        val city: WeatherCityInfo,
        val cod: String,
        val message: Double,
        val cnt: Int,
        val list: List<WeatherFF>
) : Parcelable {
    data class WeatherCityInfo(
            val id: Int,
            val name: String,
            val coord: WeatherCoordinates,
            val country: String,
            val population: Int
    ) : Parcelable {
        companion object {
            @JvmField @Suppress("unused")
            val CREATOR = object : Parcelable.Creator<WeatherCityInfo> {
                override fun createFromParcel(source: Parcel) = WeatherCityInfo(source)
                override fun newArray(size: Int): Array<WeatherCityInfo?> = arrayOfNulls(size)
            }
        }
        constructor(source: Parcel) : this(
                id = source.readInt(),
                name = source.readString(),
                coord = source.readTypedObject(WeatherCoordinates.CREATOR),
                country = source.readString(),
                population = source.readInt()
        )
        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.apply {
                writeInt(id)
                writeString(name)
                writeTypedObject(coord,0)
                writeString(country)
                writeInt(population)
            }
        }
    }

    data class WeatherFF(
            val dt: Long,
            val temp: WeatherTemp,
            val pressure: Double,
            val humidity: Int,
            val weather: List<WeatherDescription>,
            val speed: Double,
            val deg: Double,
            val clouds: Int,
            val rain: Double
    ) : Parcelable {
        companion object {
            @JvmField @Suppress("unused")
            val CREATOR = object : Parcelable.Creator<WeatherFF> {
                override fun createFromParcel(source: Parcel) = WeatherFF(source)
                override fun newArray(size: Int): Array<WeatherFF?> = arrayOfNulls(size)
            }
        }
        constructor(source: Parcel) : this(
                dt = source.readLong(),
                temp = source.readTypedObject(WeatherTemp.CREATOR),
                pressure = source.readDouble(),
                humidity = source.readInt(),
                weather = mutableListOf<WeatherDescription>().apply { source.readTypedList(this, WeatherDescription.CREATOR) },
                speed = source.readDouble(),
                deg = source.readDouble(),
                clouds = source.readInt(),
                rain = source.readDouble()
        )
        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.apply {
                writeLong(dt)
                writeTypedObject(temp,0)
                writeDouble(pressure)
                writeInt(humidity)
                writeTypedList(weather)
                writeDouble(speed)
                writeDouble(deg)
                writeInt(clouds)
                writeDouble(rain)
            }
        }
    }

    data class WeatherTemp(
            val day: Double,
            val min: Double,
            val max: Double,
            val night: Double,
            val eve: Double,
            val morn: Double
    ) : Parcelable {
        companion object {
            @JvmField @Suppress("unused")
            val CREATOR = object : Parcelable.Creator<WeatherTemp> {
                override fun createFromParcel(source: Parcel) = WeatherTemp(source)
                override fun newArray(size: Int): Array<WeatherTemp?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(
                day = source.readDouble(),
                min = source.readDouble(),
                max = source.readDouble(),
                night = source.readDouble(),
                eve = source.readDouble(),
                morn = source.readDouble()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.apply {
                writeDouble(day)
                writeDouble(min)
                writeDouble(max)
                writeDouble(night)
                writeDouble(eve)
                writeDouble(morn)
            }
        }
    }

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = object : Parcelable.Creator<WeatherForecast> {
            override fun createFromParcel(source: Parcel) = WeatherForecast(source)
            override fun newArray(size: Int): Array<WeatherForecast?> = arrayOfNulls(size)
        }
    }
    constructor(source: Parcel) : this(
            city = source.readTypedObject(WeatherCityInfo.CREATOR),
            cod = source.readString(),
            message = source.readDouble(),
            cnt = source.readInt(),
            list= mutableListOf<WeatherFF>().apply { source.readTypedList(this, WeatherFF.CREATOR) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeTypedObject(city,0)
            writeString(cod)
            writeDouble(message)
            writeInt(cnt)
            writeTypedList(list)
        }
    }
}