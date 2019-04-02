package id.pertadima.room

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import id.pertadima.room.deps.AppComponent
import id.pertadima.room.deps.DaggerAppComponent
import id.pertadima.room.deps.module.AppModule

/**
 * Created by pertadima on 01,April,2019
 */

class MainApp : DaggerApplication() {

    private lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent
            .builder()
            .create(this)
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
        return appComponent
    }
}