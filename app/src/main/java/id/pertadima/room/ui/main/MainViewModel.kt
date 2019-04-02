package id.pertadima.room.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.pertadima.room.deps.ActivityScoped
import id.pertadima.room.room.NoteRepository
import id.pertadima.room.room.entity.Note
import javax.inject.Inject

/**
 * Created by pertadima on 01,April,2019
 */

@ActivityScoped
class MainViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    private val noteList = MutableLiveData<List<Note>?>()
    fun observeNoteList(): LiveData<List<Note>?> = noteList

    init {
        noteList.postValue(noteRepository.getAllNotes().value)
    }

    fun getNoteList() {
        Log.e("IRFAN", "data: ${noteRepository.getAllNotes().value}");
        noteList.postValue(noteRepository.getAllNotes().value)
    }

    fun insertNoteList(note: Note) {
        noteRepository.insert(note)
    }
 }