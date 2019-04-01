package id.pertadima.room.deps

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import id.pertadima.room.MainApp
import id.pertadima.room.deps.module.AppModule
import javax.inject.Singleton

/**
 * Created by pertadima on 01,April,2019
 */

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)

interface AppComponent : AndroidInjector<MainApp> {
    fun inject(instance: DaggerApplication)
    override fun inject(application: MainApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder

        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
}