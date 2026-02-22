package com.example.newsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        name = findViewById(R.id.editTextName)
        email = findViewById(R.id.editTextEmail)
        phone = findViewById(R.id.editTextPhone)
        password = findViewById(R.id.editTextPassword)
        confirmPassword = findViewById(R.id.editTextConfirmPassword)
        registerButton = findViewById(R.id.buttonRegister)

        registerButton.setOnClickListener {
            validateAndRegister()
        }
    }

    private fun validateAndRegister() {

        val nameText = name.text.toString().trim()
        val emailText = email.text.toString().trim()
        val phoneText = phone.text.toString().trim()
        val passwordText = password.text.toString().trim()
        val confirmPasswordText = confirmPassword.text.toString().trim()

        if (nameText.isEmpty() ||
            emailText.isEmpty() ||
            phoneText.isEmpty() ||
            passwordText.isEmpty() ||
            confirmPasswordText.isEmpty()
        ) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        if (passwordText != confirmPasswordText) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // If validation successful
        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

        // Move to MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}