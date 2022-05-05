package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(val name: String,
                val email: String,
                val phone: String,
                @PrimaryKey(autoGenerate = false) val id: Int? = null)