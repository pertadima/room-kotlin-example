package id.pertadima.room.ui

import android.os.Bundle
import id.pertadima.room.R
import id.pertadima.room.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {

    }
}
