package id.pertadima.room.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.pertadima.room.room.entity.Note


/**
 * Created by pertadima on 01,April,2019
 */

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Delete
    fun remove(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(note: Note): Int

    @Query("SELECT * FROM notes_table ")
    fun getAllNotes(): LiveData<List<Note>>

}