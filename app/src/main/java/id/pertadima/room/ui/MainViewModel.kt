package id.pertadima.room.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.pertadima.room.deps.ActivityScoped
import id.pertadima.room.room.NoteRepository
import id.pertadima.room.room.entity.Note
import javax.inject.Inject

/**
 * Created by pertadima on 01,April,2019
 */

@ActivityScoped
class MainViewModel @Inject constructor(noteRepository: NoteRepository) {
    private val noteList = MutableLiveData<List<Note>?>()
    fun observeNoteList(): LiveData<List<Note>?> = noteList

    init {
        noteList.postValue(noteRepository.getAllNotes().value)
    }
}