package id.pertadima.room.ui.main

import androidx.lifecycle.LiveData
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
    private var allNotes: LiveData<List<Note>> = noteRepository.getAllNotes()

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }

    fun insertNoteList(note: Note) {
        noteRepository.insert(note)
    }

    fun removeNote(note: Note) {
        noteRepository.removeNote(note)
    }

    fun updateNote(note: Note) {
        noteRepository.updateNote(note)
    }
}