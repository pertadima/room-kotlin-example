package id.pertadima.room.deps.module

import android.app.Application
import dagger.Module
import dagger.Provides
import id.pertadima.room.deps.builder.ActivityBuilder
import id.pertadima.room.room.NoteRepository
import javax.inject.Singleton

/**
 * Created by pertadima on 31,March,2019
 */

@Module(
    includes = [
        ActivityBuilder::class
    ]
)
open class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun providesNoteInstanceRepository(): NoteRepository = NoteRepository(application)
}