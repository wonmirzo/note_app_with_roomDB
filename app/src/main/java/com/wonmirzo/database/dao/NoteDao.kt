package com.wonmirzo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wonmirzo.database.entity.Note

@Dao
interface NoteDao {

    @Insert()
    fun insertNote(note: Note)

    @Query("SELECT * FROM Notes")
    fun getAllNotes(): List<Note>

    @Query("DELETE FROM Notes")

    fun clearNoteList()
}