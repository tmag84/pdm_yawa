package isel.pdm.yawa.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.Toolbar
import android.view.Menu

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val layoutResId: Int

    protected open val actionBarId: Int? = null
    protected open val actionBarMenuResId: Int? = null

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