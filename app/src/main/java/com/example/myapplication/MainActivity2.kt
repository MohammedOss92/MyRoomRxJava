package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.huawei.todolist.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etPhone: EditText
    lateinit var saveButton: Button
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        etName = findViewById(R.id.nameInput) as EditText
        etEmail = findViewById(R.id.emailInput) as EditText
        etPhone = findViewById(R.id.phoneInput) as EditText
        saveButton = findViewById(R.id.saveButton) as Button

        viewModel = ViewModelProviders.of(this)[NoteViewModel::class.java]
        //on below line we are getting data passsed via an intent.
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {


            //on below line we are setting data to edit text.
            val name = intent.getStringExtra("name")
            val email = intent.getStringExtra("email")
            val phone = intent.getStringExtra("phone")
            saveButton.setText("Update Note")
            etName.setText(name)
            etEmail.setText(email)
            etPhone.setText(phone)
        }

        saveButton.setOnClickListener {

            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()
            //on below line we are checking the type and then saving or updating the data.
            if (noteType.equals("Edit")) {
                if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {

                    val updatedNote = Note(name, email, phone)

                    viewModel.update(updatedNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }


//            val name = etName.text.toString()
//            val email = etEmail.text.toString()
//            val phone = etPhone.text.toString()
//            if(saveButton.text.equals("Save")) {
//                if(name.isEmpty() || email.isEmpty()  || phone.isEmpty()) {
//                    Toast.makeText(this, "please insert title and description", Toast.LENGTH_SHORT).show()
//
//                }
//                val note = Note( name, email, phone)
//                viewModel.update(note)
//                saveButton.setText("Save")
//            }
//
//            etName.setText("")
//            etEmail.setText("")
//            etPhone.setText("")

            }
        }
    }
}