package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(var con: Context, var noteList: ArrayList<Note>,var itemClickListener : ItemClickListener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {



    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val tv_name  =itemView.findViewById(R.id.text_name) as TextView
        val tv_email =itemView.findViewById(R.id.text_email) as TextView
        val tv_phone =itemView.findViewById(R.id.text_phone) as TextView
        val cardView =itemView.findViewById(R.id.cardview) as CardView



    }
//    fun getNoteAt(position: Int) = getItemId(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(con).inflate(R.layout.note_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(getItemId(position)) {
            var infoNote = noteList[position]
            holder.tv_name.text = infoNote.name
            holder.tv_email.text = infoNote.email
            holder.tv_phone.text = infoNote.phone
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClickListener(noteList.get(position))
            }


//            holder.tv_prior.text = infoNote.phone.toString()
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setNote(newNote : List<Note>){
        noteList = newNote as ArrayList<Note>
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note {
        return noteList[position]
    }

    interface ItemClickListener {
        fun onItemClickListener(note:Note):Unit
    }

    interface RowClickListener{
        fun onDeleteUserClickListener(note: Note)
        fun onItemClickListener(note: Note)
    }
}