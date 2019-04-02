package id.pertadima.room.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import id.pertadima.room.room.entity.Note


/**
 * Created by pertadima on 01,April,2019
 */

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Delete
    fun remove(note: Note)

    @Query("SELECT * FROM notes_table ")
    fun getAllNotes(): LiveData<List<Note>>

}