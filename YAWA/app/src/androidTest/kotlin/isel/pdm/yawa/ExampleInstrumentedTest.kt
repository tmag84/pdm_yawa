package isel.pdm.yawa

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import isel.pdm.yawa.models.WeatherCurrent
import isel.pdm.yawa.models.WeatherForecast
import isel.pdm.yawa.models.WeatherInfo
import isel.pdm.yawa.requests.GetRequest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Instrumentation test, which will execute on an Android device.

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var requestQueue: RequestQueue
    // Synchronization between test harness thread and callbacks thread
    private lateinit var latch: CountDownLatch
    private var error: AssertionError? = null

    private fun waitForCompletion() {
        try {
            if (latch.await(60, TimeUnit.SECONDS)) {
                if (error != null)
                    throw error as AssertionError
            } else {
                fail("Test harness thread timeout while waiting for completion")
            }
        } catch (_: InterruptedException) {
            fail("Test harness thread was interrupted")
        }

    }

    private fun executeAndPublishResult(assertions: () -> Unit) {
        try {
            assertions()
        } catch (error: AssertionError) {
            this.error = error
        } finally {
            latch.countDown()
        }
    }

    @Before
    fun prepare() {
        // Preparing Volley's request queue
        requestQueue = Volley.newRequestQueue(InstrumentationRegistry.getTargetContext())
        requestQueue.cache.clear()
        // Preparing test harness thread synchronization artifacts
        latch = CountDownLatch(1)
        error = null
    }

    private fun handleWeatherCurrentResult(data:WeatherCurrent, weatherInfo:WeatherInfo) {
        weatherInfo.weatherCurrent=data
        syncUp(weatherInfo)
    }

    private fun handleWeatherForecastResult(data:WeatherForecast, weatherInfo:WeatherInfo) {
        weatherInfo.weatherForecast=data
        syncUp(weatherInfo)
    }

    private fun handleError(error: VolleyError, weatherInfo:WeatherInfo) {
        weatherInfo.error=error.message
        syncUp(weatherInfo)
    }

    private fun syncUp(weatherInfo:WeatherInfo) {
        if (weatherInfo.weatherForecast!=null && weatherInfo.weatherCurrent!=null) {
            val here : String = "here"
        }
    }

    @Test
    fun test_WeatherInfo() {
        @Test
        fun parseToParcebale() {
            val weatherInfo = WeatherInfo()

            requestQueue.add(
                    GetRequest(
                            WEATHER_CURR_URL_TEST,
                            WeatherCurrent::class.java,
                            {result->handleWeatherCurrentResult(result,weatherInfo)},
                            {error->handleError(error,weatherInfo)}
                    )
            )

            //request for daily forecast information
            requestQueue.add(
                    GetRequest(
                            WEATHER_FF_URL_TEST,
                            WeatherForecast::class.java,
                            {result->handleWeatherForecastResult(result,weatherInfo)},
                            {error->handleError(error,weatherInfo)}
                    )
            )
        }
    }

    //@Test
    fun test_successCurrentWeatherRequest() {
        requestQueue.add(
                GetRequest(
                        WEATHER_CURR_URL_TEST,
                        WeatherCurrent::class.java,
                        { currentWeather -> executeAndPublishResult { assertNotNull(currentWeather) } },
                        { error -> executeAndPublishResult { fail() } }
                )
        )
        waitForCompletion()
    }

    //@Test
    fun test_successForecastWeatherRequest() {
        requestQueue.add(
                GetRequest(
                        WEATHER_FF_URL_TEST,
                        WeatherForecast::class.java,
                        { forecastWeather -> executeAndPublishResult { assertNotNull(forecastWeather) } },
                        { error -> executeAndPublishResult { fail() } }
                )
        )
        waitForCompletion()
    }
}
