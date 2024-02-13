package com.example.mypracticeapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class MainActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etAge: EditText
    lateinit var etPass: EditText
    lateinit var etConfPass: EditText
    lateinit var btnSignUp: Button
    lateinit var tvRedirectLogin: TextView
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etAge = findViewById(R.id.etAge)
        etPass = findViewById(R.id.etPass)
        etConfPass = findViewById(R.id.etConfPass)
        btnSignUp = findViewById(R.id.btnSignup)
        tvRedirectLogin = findViewById(R.id.tvLoginRedirect)
        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            signUpUser()
        }
        tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
    private fun signUpUser() {
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val age = etAge.text.toString()
        val pass = etPass.text.toString()
        val cpass = etConfPass.text.toString()

        if (name.isEmpty() || email.isEmpty() || age.isEmpty() || pass.isEmpty() || cpass.isEmpty()) {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show()
        }
        else if (pass != cpass) {
            Toast.makeText(this, "Password and Confirm Password doesn't match", Toast.LENGTH_SHORT).show()
        }
        else  {
            if (age.toInt() >= 16) {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                    auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Email sent successfully please verify it",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                        ?.addOnFailureListener { err ->
                            Toast.makeText(
                                this,
                                "Error sending email " + { err.message },
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
            else {
                Toast.makeText(this, "You are not eligible to register in this app as your age is less than 16", Toast.LENGTH_SHORT).show()
            }
        }
    }
}