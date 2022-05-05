package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.NoteAdapter.ItemClickListener
import com.huawei.todolist.NoteViewModel

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etPhone: EditText
    lateinit var saveButton: Button
    lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etName = findViewById(R.id.nameInput) as EditText
        etEmail = findViewById(R.id.emailInput) as EditText
        etPhone = findViewById(R.id.phoneInput) as EditText
        saveButton = findViewById(R.id.saveButton) as Button

        setUpRecyclerView()
        viewModel = ViewModelProviders.of(this)[NoteViewModel::class.java]
        viewModel.getAllNotes().observe(this, Observer {
            Log.i("Notes observed", "$it")

            adapter.setNote(it)
            adapter.notifyDataSetChanged()
        })

        saveButton.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()
            if(saveButton.text.equals("Save")) {
                if(name.isEmpty() || email.isEmpty()  || phone.isEmpty()) {
                    Toast.makeText(this, "please insert title and description", Toast.LENGTH_SHORT).show()

                }
                val note = Note( name, email, phone)
                viewModel.insert(note)
            } else {
                val note = Note( name, email, phone)
                viewModel.update(note)
                saveButton.setText("Save")
            }

            etName.setText("")
            etEmail.setText("")
            etPhone.setText("")

        }

        // swipe listener
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val notee = adapter.getNoteAt(viewHolder.adapterPosition)
                viewModel.delete(notee)
            }
        }).attachToRecyclerView(recyclerView)

    }

    private fun setUpRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        var noteList = ArrayList<Note>()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = NoteAdapter(this@MainActivity,noteList, object : ItemClickListener {
            override fun onItemClickListener(note: Note) {


                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                intent.putExtra("noteType", "Edit")
                intent.putExtra("name", note.name)
                intent.putExtra("email", note.email)
                intent.putExtra("phone", note.phone)
                startActivity(intent)
                finish()
            }
        })
        recyclerView.adapter = adapter
    }

     fun onItemClickListener(note: Note) {
        etName.setText(note.name)
        etEmail.setText(note.email)
        etPhone.setText(note.phone)

        saveButton.setText("Update")

    }
}