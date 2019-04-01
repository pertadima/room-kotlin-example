package id.pertadima.room.room

import android.app.Application
import androidx.lifecycle.LiveData
import dagger.Module
import id.pertadima.room.room.dao.NoteDao
import id.pertadima.room.room.entity.Note
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by pertadima on 01,April,2019
 */

@Module
open class NoteRepository(application: Application) {

    private lateinit var noteDao: NoteDao
    protected var disposables = CompositeDisposable()

    init {
        val database: NoteDatabase? = NoteDatabase.getInstance(application.applicationContext)
        database?.let {
            noteDao = it.noteDao()
        }
    }

    fun insert(note: Note) {
        noteDao.insert(note)
    }

    fun getAllNotes(): Single<List<Note>> {
        return noteDao.getAllNotes()
    }
}