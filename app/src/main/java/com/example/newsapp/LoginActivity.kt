package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

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

        if (nameText.length < 3) {
            name.error = "Name must be at least 3 characters"
            name.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.error = "Enter a valid email address"
            email.requestFocus()
            return
        }


        if (phoneText.length != 10 || !phoneText.all { it.isDigit() }) {
            phone.error = "Enter valid 10-digit phone number"
            phone.requestFocus()
            return
        }

        // Password validation
        if (passwordText.length < 6) {
            password.error = "Password must be at least 6 characters"
            password.requestFocus()
            return
        }


        if (passwordText != confirmPasswordText) {
            confirmPassword.error = "Passwords do not match"
            confirmPassword.requestFocus()
            return
        }


        val sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE)
        sharedPref.edit()
            .putBoolean("isLoggedIn", true)
            .putString("name", nameText)
            .putString("email", emailText)
            .putString("phone", phoneText)
            .apply()

        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}