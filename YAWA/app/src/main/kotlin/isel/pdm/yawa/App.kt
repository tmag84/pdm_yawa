package isel.pdm.yawa

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import isel.pdm.yawa.utils.UrlBuilder
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by Tiago on 24/10/2016.
 */

class App : Application() {
    lateinit var requestQueue: RequestQueue
    lateinit var urlBuilder : UrlBuilder
    val lock = ReentrantLock()

    override fun onCreate() {
        super.onCreate()
        requestQueue = Volley.newRequestQueue(this)
        urlBuilder = UrlBuilder(resources)
    }
}