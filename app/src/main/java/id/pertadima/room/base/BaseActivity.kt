package id.pertadima.room.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dagger.android.support.DaggerAppCompatActivity
import id.pertadima.room.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by pertadima on 01,April,2019
 */

abstract class BaseActivity : DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseActivity, Observer { data: T -> data.let(action) })
    }

    protected fun Disposable.track() {
        disposables.add(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSetupLayout(savedInstanceState)
        onViewReady(savedInstanceState)
    }

    fun setupToolbarProperties(
        toolbarId: Toolbar,
        tvTitle: TextView? = null,
        @StringRes title: Int = R.string.empty_string
    ) {
        setSupportActionBar(toolbarId)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tvTitle?.setText(title)
    }

    fun setupToolbarPropertiesWithBackButton(
        toolbarId: Toolbar,
        tvTitle: TextView? = null,
        @StringRes title: Int = R.string.empty_string,
        @DrawableRes drawable: Int? = R.drawable.ic_arrow_left
    ) {
        setSupportActionBar(toolbarId)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(
                null != drawable
            )
            it.setDisplayShowHomeEnabled(null != drawable)
            drawable?.let { iconUp ->
                it.setHomeAsUpIndicator(iconUp)
            }
        }
        tvTitle?.setText(title)
    }

    fun String.changeToolbarTitle(tvTitle: TextView? = null) {
        tvTitle?.text = this
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected abstract fun onSetupLayout(savedInstanceState: Bundle?)
    protected abstract fun onViewReady(savedInstanceState: Bundle?)

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}