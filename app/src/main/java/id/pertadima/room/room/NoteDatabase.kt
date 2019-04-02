package id.pertadima.room.room

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import id.pertadima.room.room.dao.NoteDao
import id.pertadima.room.room.entity.Note

/**
 * Created by pertadima on 01,April,2019
 */

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private const val DB_NAME = "db_notes"
        private var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase? {
            if (instance == null) {
                synchronized(NoteDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        NoteDatabase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: NoteDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val noteDao = db?.noteDao()

        override fun doInBackground(vararg p0: Unit?) {
            Log.e("IRFAN", "dsadsadadsadsadasdsada: ");
            noteDao?.insert(Note("Title 1", "description 1"))
            noteDao?.insert(Note("Title 2", "description 2"))
            noteDao?.insert(Note("Title 3", "description 3"))
        }
    }
}