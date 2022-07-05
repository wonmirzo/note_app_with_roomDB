package com.wonmirzo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Int? = null,
    var time: String,
    var note: String
) {

}