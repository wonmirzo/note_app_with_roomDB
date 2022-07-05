package com.wonmirzo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wonmirzo.database.dao.NoteDao
import com.wonmirzo.database.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun NoteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context, NoteDatabase::class.java, "note database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}