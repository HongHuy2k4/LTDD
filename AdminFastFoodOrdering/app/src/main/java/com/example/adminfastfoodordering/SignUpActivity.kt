package com.example.adminfastfoodordering

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfastfoodordering.databinding.ActivitySignUpBinding
import com.example.adminfastfoodordering.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {
    private lateinit var userName: String
    private lateinit var nameOfRestaurant: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Tạo Firebase Auth
        auth = Firebase.auth
        // Tạo Firebase database
        database = Firebase.database.reference

        val locationList = arrayOf("Ha Noi", "Hai Phong", "Hue","Da Nang", "Tp HCM")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
        binding.createUserButton.setOnClickListener{

            // Lấy text trong edittext
            userName = binding.name.text.toString().trim()
            nameOfRestaurant = binding.restaurantName.text.toString().trim()
            email = binding.emailOrPhone.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (userName.isBlank() || nameOfRestaurant.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }

        }
        binding.alreadyHaveAccountButton.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                saveUserData()

                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }
    }

    // Lưu dữ liệu vào database
    private fun saveUserData() {
        // Lấy text trong edittext
        userName = binding.name.text.toString().trim()
        nameOfRestaurant = binding.restaurantName.text.toString().trim()
        email = binding.emailOrPhone.text.toString().trim()
        password = binding.password.text.toString().trim()
        val user= UserModel(userName,nameOfRestaurant,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        // Lưu dữ liệu user Firebase database
        database.child("user").child(userId).setValue(user)
    }
}