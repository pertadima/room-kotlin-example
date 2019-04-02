package id.pertadima.room.room

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import dagger.Module
import id.pertadima.room.room.dao.NoteDao
import id.pertadima.room.room.entity.Note

/**
 * Created by pertadima on 01,April,2019
 */

@Module
open class NoteRepository(context: Context) {

    private lateinit var noteDao: NoteDao
    private lateinit var allNotes: LiveData<List<Note>>

    init {
        NoteDatabase.getInstance(context)?.let {
            noteDao = it.noteDao()
            allNotes = noteDao.getAllNotes()
        }
    }

    fun insert(note: Note) {
        DoInBackgroundAsync<Note> {
            noteDao.insert(note)
        }.execute()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }

    fun updateNote(note: Note) {
        DoInBackgroundAsync<Note> {
            noteDao.update(note)
        }.execute()
    }

    fun removeNote(note: Note) {
        DoInBackgroundAsync<Note> {
            noteDao.remove(note)
        }.execute()
    }


    private class DoInBackgroundAsync<T : Any>(
        private val backgroundTask: () -> Unit
    ) : AsyncTask<T, Unit, Unit>() {
        override fun doInBackground(vararg params: T) {
            backgroundTask.invoke()
        }
    }
}