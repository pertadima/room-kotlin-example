package id.pertadima.room.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by pertadima on 31,March,2019
 */

@Entity(tableName = "notes_table")
data class Note(
    var title: String,
    var description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}