package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test.databinding.ActivitySignBinding

class SignActivity : AppCompatActivity() {
    private  lateinit var binding: ActivitySignBinding
    private  lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        binding.button6.setOnClickListener {
            val editTextText2 = binding.editTextText2.text.toString()
            val editTextTextEmailAddress = binding.editTextTextEmailAddress.text.toString()
            val editTextTextPassword2 = binding.editTextTextPassword2.text.toString()
            signupDatabase(editTextText2, editTextTextEmailAddress, editTextTextPassword2)
        }

        binding.alreadyhavebutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private  fun signupDatabase(name: String, email: String, password: String){
        val insertedRowId = databaseHelper.insertUser(name, email, password)
        if (insertedRowId != -1L){
            Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show()
        }
    }


}