package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.network.RetrofitInstance
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val API_KEY = "YOUR_API_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchNews()
    }

    private fun fetchNews() {
        lifecycleScope.launch {
            try {
                Log.d("API_KEY_CHECK", API_KEY)

                val response = RetrofitInstance.api.getTopHeadlines(
                    country = "in",
                    apiKey = API_KEY
                )

                if (response.isSuccessful) {
                    Log.d("NEWS_RESPONSE", response.body().toString())
                } else {
                    Log.e("NEWS_ERROR", "Code: ${response.code()} | Body: ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                Log.e("NETWORK_EXCEPTION", e.message ?: "Exception occurred")
            }
        }
    }
}