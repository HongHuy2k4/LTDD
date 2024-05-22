package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)
        binding.loginbutton.setOnClickListener{
            val editTextText = binding.editTextText.text.toString()
            val editTextTextPassword = binding.editTextTextPassword.text.toString()
            loginDatabase(editTextText, editTextTextPassword)

        }
        binding.donthavebutton.setOnClickListener{
            val intent = Intent(this,SignActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    private  fun loginDatabase(email: String, password: String){
        val userExists = databaseHelper.readUser(email, password)
        if (userExists){
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }
}