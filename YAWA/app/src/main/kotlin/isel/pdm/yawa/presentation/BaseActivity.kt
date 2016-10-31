package isel.pdm.yawa.presentation

import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.android.volley.VolleyError
import isel.pdm.yawa.App
import isel.pdm.yawa.R
import isel.pdm.yawa.models.WeatherCurrent
import isel.pdm.yawa.models.WeatherForecast
import isel.pdm.yawa.models.WeatherInfo
import isel.pdm.yawa.requests.GetRequest
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val layoutResId: Int

    protected open val actionBarId: Int? = null
    protected open val actionBarMenuResId: Int? = null

    lateinit var latch : CountDownLatch

    protected abstract fun localHandlerActivityExecution(weatherInfo:WeatherInfo)

    open fun handleWeatherCurrentResult(data: WeatherCurrent, weatherInfo: WeatherInfo) {
        try {
            weatherInfo.weatherCurrent=data
        }
        catch(_: InterruptedException) {
            //interruptedexception
            //place error
        }
        finally{
            //signal waiting thread to sync data
            latch.countDown()
        }
    }

    protected fun getAndStoreImages(weather:WeatherInfo) {
        var collection = HashMap<String,Image>()
        //make image requests
    }

    open fun handleWeatherForecastResult(data: WeatherForecast, weatherInfo:WeatherInfo) {
        try {
            weatherInfo.weatherForecast=data
            if (weatherInfo.error!=null) {
                //if an errors has already been caught, then nothing more needs to be done in this thread
                //later, we might create a try-catch block for all errors, to create bundles for logging
                return
            }

            //if weatherCurrent is null, then give a bit of time to make sure it's not delayed
            if (weatherInfo.weatherCurrent==null) {
                if (latch.await(10, TimeUnit.SECONDS)) {
                    //check if while waiting there was an error caught
                    if (weatherInfo.error != null) {
                        //here if later might be needed for bundle errors
                        return
                    }
                    getAndStoreImages(weatherInfo)
                    localHandlerActivityExecution(weatherInfo)
                }
                else {
                    //timedout error
                }
            }
        }
        catch (_: InterruptedException) {
            //interruptedexception
            //place error
        }
    }

    open fun handleError(error: VolleyError, weatherInfo:WeatherInfo) {
        //create a mutex to make sure that WeatherInfo.error is only being handled by this thread at the moment
        synchronized((application as App).lock) {
            if (weatherInfo.error!=null) {
                weatherInfo.error= error.message
            }
            (application as App).lock.unlock()
        }
        //call errorActivity
    }

    open fun getWeatherInfo(locationString:String) {
        val requestQueue = (application as App).requestQueue
        val urlBuilder = (application as App).urlBuilder
        val language = Locale.getDefault().language
        val weatherInfo = WeatherInfo()
        latch = CountDownLatch(1)

        //request for current weather information
        requestQueue.add(
                GetRequest(
                        urlBuilder.buildWeatherCurrentUrl(locationString,language),
                        WeatherCurrent::class.java,
                        {result->handleWeatherCurrentResult(result,weatherInfo)},
                        {error->handleError(error,weatherInfo)}
                )
        )

        //request for daily forecast information
        requestQueue.add(
                GetRequest(
                        urlBuilder.buildWeatherForecastUrl(locationString,language),
                        WeatherForecast::class.java,
                        {result->handleWeatherForecastResult(result,weatherInfo)},
                        {error->handleError(error,weatherInfo)}
                )
        )
    }

    private fun initContents() {
        setContentView(layoutResId)
        actionBarId?.let {
            setSupportActionBar(findViewById(it) as Toolbar)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContents()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initContents()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        actionBarMenuResId?.let {
            menuInflater.inflate(it, menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }
}