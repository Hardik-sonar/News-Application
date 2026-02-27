package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"

        val name = findViewById<TextView>(R.id.textViewProfileName)
        val email = findViewById<TextView>(R.id.textViewProfileEmail)
        val phone = findViewById<TextView>(R.id.textViewProfilePhone)
        val logoutButton = findViewById<Button>(R.id.buttonLogout)

        val sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE)

        name.text = sharedPref.getString("name", "")
        email.text = sharedPref.getString("email", "")
        phone.text = sharedPref.getString("phone", "")

        logoutButton.setOnClickListener {

            sharedPref.edit().clear().apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}