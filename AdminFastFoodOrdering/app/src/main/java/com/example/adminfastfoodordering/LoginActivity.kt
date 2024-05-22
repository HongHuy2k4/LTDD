package com.example.adminfastfoodordering

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adminfastfoodordering.databinding.ActivityLoginBinding
import com.example.adminfastfoodordering.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {
    private var userName: String? = null
    private var nameOfRestaurant: String? = null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Tạo Firebase Auth
        auth = Firebase.auth
        // Tạo Firebase database
        database = Firebase.database.reference

        binding.loginButton.setOnClickListener{

            // Lấy text trong edittext
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }
        }
        binding.dontHaveButton.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task ->
            if (task.isSuccessful){
                val user = auth.currentUser
                Toast.makeText(this,"Login Successfully",Toast.LENGTH_SHORT).show()
                uppdateUi(user)
            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        val user = auth.currentUser
                        Toast.makeText(this,"Create User & Login Successfully",Toast.LENGTH_SHORT).show()
                        saveUserData()
                        uppdateUi(user)
                    }
                    else{
                        Toast.makeText(this,"Authentication failed", Toast.LENGTH_SHORT).show()
                        Log.d("Account", "createUserAccount: Authentication failed",task.exception)
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        // Lấy text trong edittext
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(userName,nameOfRestaurant,email,password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            if(it != null){
                database.child("user").child(it).setValue(user)
            }
        }
    }

    // Kiểm tra nếu người dùng đã đăng nhập
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    private fun uppdateUi(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}