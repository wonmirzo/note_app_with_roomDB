package com.wonmirzo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wonmirzo.R
import com.wonmirzo.database.entity.Note

class NoteAdapter(val notes: ArrayList<Note>) :
    RecyclerView.Adapter<NoteAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val note = notes[position]
        val tvNote = holder.tvNote

        tvNote.text = note.note
    }

    override fun getItemCount(): Int = notes.size

    //view holders
    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvNote: TextView = view.findViewById(R.id.tvNote)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNote(note: Note) {
        notes.add(note)
        notifyDataSetChanged()
    }
}