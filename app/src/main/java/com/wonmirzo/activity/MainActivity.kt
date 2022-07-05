package com.wonmirzo.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.wonmirzo.R
import com.wonmirzo.adapter.NoteAdapter
import com.wonmirzo.database.NoteDatabase
import com.wonmirzo.database.entity.Note

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvNotesStatus: TextView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteDatabase: NoteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        val ivTrash: ImageView = findViewById(R.id.ivTrash)
        noteDatabase = NoteDatabase.getInstance(this)!!

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        noteAdapter = NoteAdapter(getAllNotes())
        recyclerView.adapter = noteAdapter
        tvNotesStatus = findViewById(R.id.tvNotesStatus)

        val fabAddNote: ExtendedFloatingActionButton = findViewById(R.id.fabAddNote)

        fabAddNote.setOnClickListener {
            openDialog()
        }

        tvNotesStatus.isVisible = noteAdapter.notes.isEmpty()

        ivTrash.setOnClickListener {
            Toast.makeText(this, "Notes has cleared!", Toast.LENGTH_SHORT).show()
            noteDatabase.NoteDao().clearNoteList()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getAllNotes(): ArrayList<Note> {
        val notes = noteDatabase.NoteDao().getAllNotes() as ArrayList<Note>
        if (notes.isEmpty()) {
            return arrayListOf()
        }
        return notes
    }

    @SuppressLint("InflateParams")
    private fun openDialog() {
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.custom_dialog_view, null)
        val builder = AlertDialog.Builder(this).create()
        builder.setView(view)
        val etNote = view.findViewById<EditText>(R.id.etNote)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        btnCancel.setOnClickListener {
           builder.dismiss()
        }

        btnSave.setOnClickListener {
            val note = etNote!!.text.toString().trim()
            if (note.isEmpty()) {
                Toast.makeText(this, "Please write some notes", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val notE = Note(time = java.util.Calendar.getInstance().toString(), note = note)
                noteAdapter.addNote(notE)
                noteDatabase.NoteDao().insertNote(notE)
                tvNotesStatus.isVisible = noteAdapter.notes.isEmpty()
                builder.dismiss()
            }
        }
        builder.show()
    }
}