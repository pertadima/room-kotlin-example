package id.pertadima.room.deps.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.pertadima.room.deps.ActivityScoped
import id.pertadima.room.ui.MainActivity

/**
 * Created by pertadima on 01,April,2019
 */

@Module
abstract class ActivityBuilder {
    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}