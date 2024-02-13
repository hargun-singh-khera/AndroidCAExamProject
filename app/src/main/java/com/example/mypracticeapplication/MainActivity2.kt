package com.example.mypracticeapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var btnLogin: Button
    lateinit var tvRedirectSignup: TextView
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        etEmail = findViewById(R.id.etEmail)
        etPass = findViewById(R.id.etPass)
        btnLogin = findViewById(R.id.btnLogin)
        tvRedirectSignup = findViewById(R.id.tvRedirectSignup)
        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            loginUser()
        }
        tvRedirectSignup.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun loginUser() {
        auth.signInWithEmailAndPassword(etEmail.text.toString(), etPass.text.toString()).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, MainActivity3::class.java)
                startActivity(intent)
            }
        }
            .addOnFailureListener {err ->
                Toast.makeText(this, "Invalid Credentials " + {err.message}, Toast.LENGTH_SHORT).show()
            }
    }
}